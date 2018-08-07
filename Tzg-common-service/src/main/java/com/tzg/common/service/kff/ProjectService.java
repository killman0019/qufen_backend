package com.tzg.common.service.kff;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Objects;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.DozerMapperUtils;
import com.tzg.entitys.kff.evaluation.Evaluation;
import com.tzg.entitys.kff.evaluation.EvaluationMapper;
import com.tzg.entitys.kff.post.Post;
import com.tzg.entitys.kff.post.PostMapper;
import com.tzg.entitys.kff.praise.Praise;
import com.tzg.entitys.kff.praise.PraiseMapper;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.entitys.kff.project.KFFProjectMapper;
import com.tzg.entitys.kff.project.ProjectResponse;
import com.tzg.entitys.kff.projecttrade.ProjectTrade;
import com.tzg.entitys.kff.projecttrade.ProjectTradeMapper;
import com.tzg.entitys.kff.qfindex.QfIndex;
import com.tzg.entitys.kff.qfindex.QfIndexMapper;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value = "KFFProjectService")
@Transactional
public class ProjectService {
	private static final Log logger = LogFactory.getLog(ProjectService.class);

	@Autowired
	private KFFProjectMapper projectMapper;
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private EvaluationMapper evaluationMapper;
	@Autowired
	private QfIndexMapper qfIndexMapper;
	@Autowired
	private PraiseMapper praiseMapper;

	@Autowired
	private ProjectTradeMapper projectTradeMapper;

	private static final ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 4);

	public List<KFFProject> findListByMap(Map<String, Object> map) {
		return projectMapper.findListByMap(map);
	}

	@Transactional(readOnly = true)
	public KFFProject findById(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		return projectMapper.findById(id);
	}

	public void delete(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		projectMapper.deleteById(id);
	}

	public void save(KFFProject project) throws RestServiceException {
		projectMapper.save(project);
	}

	public void update(KFFProject project) throws RestServiceException {
		if (project.getProjectId() == null) {
			throw new RestServiceException("id不能为空");
		}
		projectMapper.update(project);
	}

	@Transactional(readOnly = true)
	public PageResult<KFFProject> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<KFFProject> result = null;
		try {
			Integer count = projectMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<KFFProject> list = projectMapper.findPage(query.getQueryData());
				result = new PageResult<KFFProject>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<KFFProject> findProjectByCode(Map<String, Object> map) {

		return projectMapper.findProjectByCode(map);
	}

	public void increaseFollowerNum(Integer projectId) throws RestServiceException {
		if (projectId == null || projectId == 0) {
			throw new RestServiceException("项目ID错误");
		}
		projectMapper.increaseFollowerNum(projectId);
	}

	public void decreaseFollowerNum(Integer projectId) {

		projectMapper.decreaseFollowerNum(projectId);
	}

	@Transactional(readOnly = true)
	public List<KFFProject> findProjectName() {
		// TODO Auto-generated method stub
		return projectMapper.findProjectName();
	}

	@Transactional(readOnly = true)
	public KFFProject findProjectIdByCodeAndChineseName(KFFProject kffProject) {
		// TODO Auto-generated method stub
		return projectMapper.findProjectIdByCodeAndChineseName(kffProject);
	}

	public void increaseRaterNum(Integer projectId) {
		if (projectId == null || projectId == 0) {
			// throw new RestServiceException("项目ID错误");
			return;
		}
		projectMapper.increaseRaterNum(projectId);

	}

	public void updateTotalScore(Integer projectId, BigDecimal totalScore) {

		if (projectId == null || projectId == 0) {
			// throw new RestServiceException("项目ID错误");
			return;
		}
		if (totalScore == null || totalScore.compareTo(BigDecimal.ZERO) <= 0) {
			return;
		}
		KFFProject original = projectMapper.findById(projectId);
		if (original == null) {
			return;
		}
		KFFProject project = new KFFProject();
		project.setProjectId(projectId);
		project.setUpdateTime(new Date());
		BigDecimal originalTotal = original.getTotalScore() == null ? BigDecimal.ZERO : original.getTotalScore();
		int raterNum = original.getRaterNum() == null ? 0 : original.getRaterNum();
		if (raterNum == 0) {
			return;
		}
		originalTotal = originalTotal.multiply(new BigDecimal(raterNum));
		totalScore = originalTotal.add(totalScore);
		totalScore = totalScore.divide(new BigDecimal(raterNum + 1), 1, RoundingMode.HALF_DOWN).setScale(1, RoundingMode.HALF_DOWN);
		project.setTotalScore(totalScore);

		projectMapper.updateTotalScore(project);
	}

	public void caculateProjectTotalScore() {

		List<Post> projects = postMapper.getProjectIdsGreateThanTenEvas();
		if (CollectionUtils.isEmpty(projects)) {
			logger.warn("no projects with more than 1 evaluations");
			return;
		}
		final CountDownLatch countDownLatch = new CountDownLatch(projects.size());

		for (Post p : projects) {
			final Integer projectId = p.getProjectId();
			newFixedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						caculateOneScore(projectId, countDownLatch);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void caculateOneScore(Integer projectId, CountDownLatch countDownLatch) {
		try {
			BigDecimal totalScore;

			KFFProject originalProject = projectMapper.findById(projectId);
			if (originalProject != null) {
				totalScore = originalProject.getTotalScore();

				// 获取简单评测，全面专业评测 和 自定义评测统计
				List<Evaluation> evas = evaluationMapper.getAllEvasExceptPartByProjectId(projectId);
				// 根据projectid统计简单评测,完整评测和自定义评测的打分人数
				Integer totalRaterNum = evaluationMapper.countEvaExportOnlyEva(projectId);
				if (CollectionUtils.isEmpty(evas)) {
					countDownLatch.countDown();
					return;
				}
				QfIndex qfIndex = null;
				Integer ownerQfScore = 100;
				BigDecimal totalDividor = BigDecimal.ZERO;
				Long totalDividend = 0L;
				for (Evaluation eva : evas) {
					BigDecimal oneDividor = BigDecimal.ZERO;
					Long oneDividend = 0L;
					BigDecimal oneScore = eva.getTotalScore() == null ? BigDecimal.ZERO : eva.getTotalScore();// 取分数
					qfIndex = qfIndexMapper.findByUserId(eva.getCreateUserId());
					if (qfIndex != null) {
						ownerQfScore = qfIndex.getStatusHierarchyType() == null ? 100 : qfIndex.getStatusHierarchyType();// 取区分指数
					}
					// 简单评测
					if (Objects.equal(1, eva.getModelType())) {
						oneDividor = oneScore.multiply(new BigDecimal(ownerQfScore)).multiply(new BigDecimal(100));
						oneDividend = ownerQfScore * 100L;
					} else if (Objects.equal(2, eva.getModelType()) || Objects.equal(4, eva.getModelType())) {
						// 系统专业评测 //用户自定义评测
						Map<String, Object> map = new HashMap<>();
						map.put("status", "1");
						map.put("postType", "1");
						map.put("postId", eva.getPostId());
						map.put("projectId", projectId + "");
						map.put("praiseType", "1");
						List<Praise> praises = praiseMapper.findAllActivePraisesByPostId(map);
						if (CollectionUtils.isEmpty(praises)) {// 没有赞
							oneDividor = oneScore.multiply(new BigDecimal(ownerQfScore)).multiply(new BigDecimal(100));
							oneDividend = ownerQfScore * 100L;
						} else {// 有赞
							List<Integer> praiseUserIds = new ArrayList<>();
							for (Praise p : praises) {
								praiseUserIds.add(p.getPraiseUserId());
							}
							Map<String, Object> qfUsersMap = new HashMap<>();
							String userIds = StringUtils.join(praiseUserIds.toArray(), ",");
							qfUsersMap.put("userIds", userIds);
							List<QfIndex> priaseUserQfIndexs = qfIndexMapper.findByUserIds(qfUsersMap);
							if (!CollectionUtils.isEmpty(priaseUserQfIndexs)) {
								Integer totalQf = 0;
								for (QfIndex qf : priaseUserQfIndexs) {
									totalQf = totalQf + (qf.getStatusHierarchyType() == null ? 100 : qf.getStatusHierarchyType());
								}
								if (totalQf == 0)
									totalQf = 100;
								oneDividor = oneScore.multiply(new BigDecimal(ownerQfScore)).multiply(new BigDecimal(totalQf));
								oneDividend = ownerQfScore * (Long.valueOf(totalQf));
							} else {
								oneDividor = oneScore.multiply(new BigDecimal(ownerQfScore)).multiply(new BigDecimal(100));
								oneDividend = ownerQfScore * 100L;
							}
						}
					}
					totalDividor = totalDividor.add(oneDividor);
					totalDividend = totalDividend + oneDividend;
				}
				BigDecimal originalTotalScore = totalScore;
				if (totalDividend != 0) {
					totalScore = totalDividor.divide(new BigDecimal(totalDividend), 1, RoundingMode.HALF_DOWN);
				}
				KFFProject project = new KFFProject();
				project.setProjectId(projectId);
				project.setUpdateTime(new Date());
				project.setTotalScore(totalScore);
				project.setTotalRaterNum(totalRaterNum);
				projectMapper.updateTotalScore(project);

				logger.warn("------update project totalSocre for project: " + projectId + "originalScore: " + originalTotalScore + "newScore: " + totalScore
						+ "totalRaterNum: " + totalRaterNum);

			}
		} catch (Exception e) {
			logger.error("failed update project totalScore for project:" + projectId + ",error:{}" + e.getMessage());
		} finally {
			countDownLatch.countDown();
		}
	}

	/**
	 * self编写
	 * 
	 * @param projectId
	 * @param countDownLatch
	 */
	private void caculateOneScoreSelf(Integer projectId, CountDownLatch countDownLatch) {
		try {
			BigDecimal totalScore;

			KFFProject originalProject = projectMapper.findById(projectId);
			if (originalProject != null) {
				totalScore = originalProject.getTotalScore();

				// 获取简单评测，全面专业评测 和 自定义评测统计
				List<Evaluation> evas = evaluationMapper.getAllEvasExceptPartByProjectId(projectId);
				if (CollectionUtils.isEmpty(evas)) {
					countDownLatch.countDown();
					return;
				}
				QfIndex qfIndex = null;
				Integer ownerQfScore = 100;
				BigDecimal totalDividor = BigDecimal.ZERO;
				Long totalDividend = 0L;
				for (Evaluation eva : evas) {
					BigDecimal oneDividor = BigDecimal.ZERO;
					Long oneDividend = 0L;
					BigDecimal oneScore = eva.getTotalScore() == null ? BigDecimal.ZERO : eva.getTotalScore();
					qfIndex = qfIndexMapper.findByUserId(eva.getCreateUserId());
					if (qfIndex != null) {
						ownerQfScore = qfIndex.getStatusHierarchyType() == null ? 100 : qfIndex.getStatusHierarchyType();

					}
					// 简单评测
					if (Objects.equal(1, eva.getModelType())) {
						oneDividor = oneScore.multiply(new BigDecimal(ownerQfScore)).multiply(new BigDecimal(100));// 上
						oneDividend = ownerQfScore * 100L;// 下
					} else if (Objects.equal(2, eva.getModelType()) || Objects.equal(4, eva.getModelType())) {
						// 系统专业评测 //用户自定义评测
						Map<String, Object> map = new HashMap<>();
						map.put("status", "1");
						map.put("postType", "1");
						map.put("postId", eva.getPostId());
						map.put("projectId", projectId + "");
						map.put("praiseType", "1");
						List<Praise> praises = praiseMapper.findAllActivePraisesByPostId(map);
						if (CollectionUtils.isEmpty(praises)) {// 没有赞
							oneDividor = oneScore.multiply(new BigDecimal(ownerQfScore)).multiply(new BigDecimal(100));
							oneDividend = ownerQfScore * 100L;
						} else {// 有赞
							List<Integer> praiseUserIds = new ArrayList<>();
							for (Praise p : praises) {
								praiseUserIds.add(p.getPraiseUserId());
							}
							Map<String, Object> qfUsersMap = new HashMap<>();
							String userIds = StringUtils.join(praiseUserIds.toArray(), ",");
							System.out.println("userIds" + userIds);
							qfUsersMap.put("userIds", userIds);
							List<QfIndex> priaseUserQfIndexs = qfIndexMapper.findByUserIds(qfUsersMap);
							if (!CollectionUtils.isEmpty(priaseUserQfIndexs)) {// 没人点赞
								Integer totalQf = 0;
								for (QfIndex qf : priaseUserQfIndexs) {
									totalQf = totalQf + (qf.getStatusHierarchyType() == null ? 100 : qf.getStatusHierarchyType());
								}
								if (totalQf == 0)
									totalQf = 100;
								oneDividor = oneScore.multiply(new BigDecimal(ownerQfScore)).multiply(new BigDecimal(totalQf));
								oneDividend = ownerQfScore * (Long.valueOf(totalQf));
							} else {
								oneDividor = oneScore.multiply(new BigDecimal(ownerQfScore)).multiply(new BigDecimal(100));
								oneDividend = ownerQfScore * 100L;
							}
						}
					}
					totalDividor = totalDividor.add(oneDividor);
					totalDividend = totalDividend + oneDividend;
				}
				BigDecimal originalTotalScore = totalScore;
				if (totalDividend != 0) {
					totalScore = totalDividor.divide(new BigDecimal(totalDividend), 1, RoundingMode.HALF_DOWN);// 保留一位
																												// 四舍五入
				}
				KFFProject project = new KFFProject();
				project.setProjectId(projectId);
				project.setUpdateTime(new Date());
				project.setTotalScore(totalScore);
				projectMapper.updateTotalScore(project);

				logger.warn("------update project totalSocre for project:" + projectId + "originalScore:" + originalTotalScore + "newScore:" + totalScore);

			}
		} catch (Exception e) {
			logger.error("failed update project totalScore for project:" + projectId + ",error:{}" + e.getMessage());
		} finally {
			countDownLatch.countDown();
		}
	}

	/**
	 * 
	 * TODO
	 * @param query
	 * @param projectFollowList 关注的projectIdlist
	 * @return
	 * @author zhangdd
	 * @data 2018年8月7日
	 *
	 */
	@Transactional(readOnly = true)
	public PageResult<ProjectResponse> selectPage(PaginationQuery query, List<Integer> projectFollowList) {
		// TODO 使用in 进行分页查询
		List<ProjectResponse> projectResponses = new ArrayList<ProjectResponse>();

		PageResult<ProjectResponse> result = null;
		try {
			Integer count = projectMapper.findPageCountInList(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<KFFProject> list = projectMapper.findPageInList(query.getQueryData());
				if (!CollectionUtils.isEmpty(list)) {
					System.err.println("list" + JSON.toJSONString(list));
					for (KFFProject kffProject : list) {
						if (null != kffProject) {
							System.err.println("kffProject" + JSON.toJSONString(kffProject));
							ProjectResponse projectResponse = new ProjectResponse();
							DozerMapperUtils.map(kffProject, projectResponse);
							Map<String, String> projectTradeMap = new HashMap<String, String>();

							projectTradeMap.put("projectId", kffProject.getProjectId() + "");
							List<ProjectTrade> projectTradeList = projectTradeMapper.findByMap(projectTradeMap);
							if (!CollectionUtils.isEmpty(projectTradeList)) {
								ProjectTrade projectTrade = projectTradeList.get(0);
								projectResponse.setPercentChange1h(projectTrade.getPercentChange1h());
								projectResponse.setPercentChange24h(projectTrade.getPercentChange24h());
								projectResponse.setPercentChange7d(projectTrade.getPercentChange7d());
								projectResponse.setPrice(projectTrade.getPrice());
								projectResponse.setMarketCap(projectTrade.getMarketCap());
								projectResponse.setVolume24h(projectTrade.getVolume24h());
								projectResponse.setRank(projectTrade.getRank());
								if (!CollectionUtils.isEmpty(projectFollowList)) {
									if (projectFollowList.contains(projectResponse.getProjectId())) {
										projectResponse.setFollowStatus(1);
									}
								}
							}
							projectResponses.add(projectResponse);
						}
					}
				}
				result = new PageResult<ProjectResponse>(projectResponses, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public List<KFFProject> findByMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return projectMapper.findByMap(map);
	}

	/**
	 * 
	 * TODO 查询projectand trade 相关信息
	 * @param query
	 * @return
	 * @author zhangdd
	 * @data 2018年8月7日
	 *
	 */
	public PageResult<ProjectResponse> findAllProjectAndTrade(PaginationQuery query) {
		// TODO Auto-generated method stub
		
		PageResult<ProjectResponse> result = null;
		try {
			Integer count = projectMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<ProjectResponse> list = projectMapper.findAllProjectAndTrade(query.getQueryData());
				result = new PageResult<ProjectResponse>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

}
