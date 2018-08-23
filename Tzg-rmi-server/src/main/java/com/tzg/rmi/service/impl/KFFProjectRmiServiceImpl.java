package com.tzg.rmi.service.impl;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.remoting.exchange.ExchangeServer;
import com.alibaba.fastjson.JSON;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.redis.RedisService;
import com.tzg.common.service.kff.ArticleService;
import com.tzg.common.service.kff.AuthenticationService;
import com.tzg.common.service.kff.AwardPortService;
import com.tzg.common.service.kff.CoinPropertyService;
import com.tzg.common.service.kff.CollectService;
import com.tzg.common.service.kff.CommendationService;
import com.tzg.common.service.kff.CommentsService;
import com.tzg.common.service.kff.DareasService;
import com.tzg.common.service.kff.DevaluationModelDetailService;
import com.tzg.common.service.kff.DevaluationModelService;
import com.tzg.common.service.kff.DiscussService;
import com.tzg.common.service.kff.DprojectTypeService;
import com.tzg.common.service.kff.DtagsService;
import com.tzg.common.service.kff.EvaluationService;
import com.tzg.common.service.kff.ExchangeService;
import com.tzg.common.service.kff.FollowService;
import com.tzg.common.service.kff.MessageService;
import com.tzg.common.service.kff.MobileversionupdateService;
import com.tzg.common.service.kff.NoticeService;
import com.tzg.common.service.kff.PostService;
import com.tzg.common.service.kff.PraiseService;
import com.tzg.common.service.kff.ProjectForTabService;
import com.tzg.common.service.kff.ProjectManageService;
import com.tzg.common.service.kff.ProjectService;
import com.tzg.common.service.kff.ProjectTradeService;
import com.tzg.common.service.kff.ProjectevastatService;
import com.tzg.common.service.kff.QfIndexService;
import com.tzg.common.service.kff.SuggestService;
import com.tzg.common.service.kff.TokenawardService;
import com.tzg.common.service.kff.TokenrecordsService;
import com.tzg.common.service.kff.TransactionPairService;
import com.tzg.common.service.kff.UserCardService;
import com.tzg.common.service.kff.UserInvationService;
import com.tzg.common.service.kff.UserService;
import com.tzg.common.service.kff.UserWalletService;
import com.tzg.common.service.systemParam.SystemParamService;
import com.tzg.common.utils.DozerMapperUtils;
import com.tzg.common.zookeeper.ZKClient;
import com.tzg.entitys.kff.follow.Follow;
import com.tzg.entitys.kff.follow.FollowResponse;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.entitys.kff.project.ProjectResponse;
import com.tzg.entitys.kff.projectManage.ProjectManage;
import com.tzg.entitys.kff.projectManage.ProjectManageResponse;
import com.tzg.entitys.kff.projectManage.ProjectManageTabResponse;
import com.tzg.entitys.kff.projecttrade.ProjectTrade;
import com.tzg.entitys.kff.transactionpair.TransactionPair;
import com.tzg.entitys.kff.transactionpair.TransactionPairResponse;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.kff.userwallet.KFFUserWalletMapper;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rmi.service.KFFProjectRmiService;
import com.tzg.rmi.service.KFFRmiService;
import com.tzg.rmi.service.SmsSendRmiService;

public class KFFProjectRmiServiceImpl implements KFFProjectRmiService {

	@Autowired
	private KFFRmiService kffRmiService;
	@Autowired
	private ArticleService kffArticleService;
	@Autowired
	private CollectService kffCollectService;
	@Autowired
	private CommentsService kffCommentsService;
	@Autowired
	private DiscussService kffDiscussService;
	@Autowired
	private EvaluationService kffEvaluationService;
	@Autowired
	private DevaluationModelService kffDevaluationModelService;
	@Autowired
	private DevaluationModelDetailService kffDevaluationModelDetailService;
	@Autowired
	private FollowService kffFollowService;
	@Autowired
	private PostService kffPostService;
	@Autowired
	private MessageService kffMessageService;
	@Autowired
	private MobileversionupdateService kffMobileversionupdateService;
	@Autowired
	private PraiseService kffPraiseService;
	@Autowired
	private ProjectService kffProjectService;
	@Autowired
	private TokenrecordsService kffTokenrecordsService;
	@Autowired
	private UserService kffUserService;
	@Autowired
	private NoticeService kffNoticeService;
	@Autowired
	private SuggestService kffSuggestService;
	@Autowired
	private DareasService kffDareasService;
	@Autowired
	private DtagsService kffDtagsService;
	@Autowired
	private DprojectTypeService kffDprojectTypeService;
	@Autowired
	private CommendationService kffCommendationService;
	@Autowired
	private ProjectevastatService kffProjectevastatService;
	@Autowired
	private UserCardService userCardService;
	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private SmsSendRmiService smsSendRmiService;
	@Autowired
	private UserInvationService userInvationService;
	@Autowired
	private QfIndexService qfIndexService;
	@Autowired
	private CoinPropertyService coinPropertyService;
	@Autowired
	private TokenawardService tokenawardService;
	@Autowired
	private UserWalletService userWalletService;
	@Autowired
	private TokenawardService kffTokenawardService;
	@Autowired
	private AwardPortService awardPortService;
	@Autowired
	private KFFUserWalletMapper kFFUserWalletMapper;
	@Autowired
	private ZKClient zkClient;
	@Autowired
	private SystemParamService systemParamService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private ProjectManageService projectManageService;

	@Autowired
	private ProjectForTabService projectForTabService;

	@Autowired
	private ExchangeService exchangeService;

	@Autowired
	private TransactionPairService transactionPairService;
	@Autowired
	private ProjectTradeService projectTradeService;

	/**
	 * 1 全部 2 关注
	 */
	@Override
	public List<ProjectManageTabResponse> showTab() throws RestServiceException {
		// TODO 向前台展示tab栏
		List<ProjectManage> projectManages = projectManageService.selectAllTab();
		List<ProjectManageTabResponse> tabList = new ArrayList<ProjectManageTabResponse>();
		Map<Integer, ProjectManageTabResponse> tabMap = new HashMap<Integer, ProjectManageTabResponse>();
		if (!CollectionUtils.isEmpty(projectManages)) {

			for (ProjectManage projectManage : projectManages) {
				if (null != projectManage) {
					Integer tabOrderNumber = projectManage.getTabOrderNumber();// tab 排列序号
					boolean isContainsKey = tabMap.containsKey(tabOrderNumber);
					if (isContainsKey) {// 包含这个key
						throw new RestServiceException("多次tab值使用同一个序号,请联系客服!");
					}
					ProjectManageTabResponse projectManageTabResponse = new ProjectManageTabResponse();
					DozerMapperUtils.map(projectManage, projectManageTabResponse);
					tabMap.put(tabOrderNumber, projectManageTabResponse);
					tabList = sortMapTab(tabMap);// 对tab键进行 了排序

				}
			}
		}
		System.err.println(JSON.toJSONString(tabList));

		return tabList;
	}

	private PageResult<ProjectResponse> selectProjectsListByProjectCodePage(Integer tabId, PaginationQuery query, Integer userId,
			List<Integer> projectFollowList) {
		// TODO 根据projectList查询project 并用分页进行展示
		PageResult<ProjectResponse> projectResponsePage = null;

		// List<Integer> followedProjectIds = new ArrayList<Integer>();
		List<Integer> projectIds = new ArrayList<Integer>();
		// 判断此项目是否被这个用户 关注

		if (null != tabId) {

			List<String> projectCodes = projectForTabService.selectProjectCodeByTabid(tabId);
			if (!CollectionUtils.isEmpty(projectCodes)) {
				if (projectCodes.size() != 0) {
					for (String projectCode : projectCodes) {

						if (StringUtils.isNotEmpty(projectCode)) {
							// 根据projectcode查询project对象
							KFFProject projectResponse = selectProjectByCode(projectCode, userId);
							// 将审核通过的项目放入队列中
							if (null != projectResponse && projectResponse.getState() == 2) {
								projectIds.add(projectResponse.getProjectId());
							}
						}
					}
				}
				System.err.println("projectIds" + JSON.toJSONString(projectIds));
				if (!CollectionUtils.isEmpty(projectIds)) {
					if (projectIds.size() == 1) {
						query.addQueryData("projectId", projectIds.get(0));
					} else {
						String projectIdListDB = StringUtils.join(projectIds.toArray(), ",");// 进入数据库的查询参数
						query.addQueryData("inList", projectIdListDB);
					}
				}
			}

			projectResponsePage = kffProjectService.selectPage(query, projectFollowList);
		}
		return projectResponsePage;
	}

	/**
	 * 根据codelist 查询项目列表
	 * 
	 * @param projectList
	 * @return
	 */

	/*private List<ProjectResponse> selectProjectsListByProjectCodeList(String projectList, PaginationQuery query, Integer userId) {
		// TODO Auto-generated method stub
		projectList = "TRX,ELF,SMT,ADA";
		List<ProjectResponse> projectResponseList = new ArrayList<ProjectResponse>();

		List<Integer> followedProjectIds = new ArrayList<Integer>();
		// 判断此项目是否被这个用户 关注
		PaginationQuery queryFollow = new PaginationQuery();
		queryFollow.addQueryData("followUserId", userId + "");
		queryFollow.addQueryData("followType", "1"); // 关注类型：1-关注项目;2-关注帖子；3-关注用户
		queryFollow.addQueryData("status", "1");
		queryFollow.setPageIndex(1);
		queryFollow.setRowsPerPage(2000);
		PageResult<Follow> follows = kffFollowService.findPage(queryFollow);
		if (follows != null && !CollectionUtils.isEmpty((follows.getRows()))) {
			for (Follow follow : follows.getRows()) {
				followedProjectIds.add(follow.getFollowedId());
			}
		}

		if (StringUtils.isNotEmpty(projectList)) {
			String[] projectCodes = projectList.split(",");
			if (projectCodes.length != 0) {
				for (int i = 0; i < projectCodes.length; i++) {
					String projectCode = projectCodes[i];
					if (StringUtils.isNotEmpty(projectCode)) {
						// 根据projectcode查询project对象
						ProjectResponse projectResponse = selectProjectByCode(projectCode, userId);

						if (null != projectResponse) {
							// 0-未关注；1-已关注
							if (null != projectResponse.getProjectId() && followedProjectIds.contains(projectResponse.getProjectId())) {
								projectResponse.setFollowStatus(1);// 设置成关注
							} else {
								projectResponse.setFollowStatus(0);// 设置成未关注
							}
						}

						projectResponseList.add(projectResponse);
					}
				}
			}
		}
		return projectResponseList;
	}*/

	/**
	 * 根据code查询project
	 * 
	 * @param projectCode
	 * @param userId
	 * @return
	 */
	private KFFProject selectProjectByCode(String projectCode, Integer userId) {
		// TODO 根据code查询project
		KFFProject kffProject = null;
		if (StringUtils.isNotEmpty(projectCode)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("projectCode", projectCode);
			List<KFFProject> project = kffProjectService.findByMap(map);
			if (!CollectionUtils.isEmpty(project) && project.size() == 1) {
				kffProject = project.get(0);

			}
		}
		return kffProject;
	}

	/**
	 * 对key 是integer类型的进行排序
	 * 
	 * @param mapIn
	 * @return
	 */
	private static List<ProjectManageTabResponse> sortMapTab(Map<Integer, ProjectManageTabResponse> mapIn) {
		List<ProjectManageTabResponse> list = new ArrayList<ProjectManageTabResponse>();
		Map<Integer, ProjectManageTabResponse> map = new TreeMap<Integer, ProjectManageTabResponse>(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				return o1 - o2;
			}
		});
		map.putAll(mapIn);
		Set<Integer> keySet = map.keySet();
		Iterator<Integer> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			Integer next = iterator.next();
			ProjectManageTabResponse value = map.get(next);
			if (null != value) {
				list.add(value);
			}

		}
		return list;

	}

	/**
	 * 对key 是integer类型的进行排序
	 * 
	 * @param mapIn
	 * @return
	 */
	@SuppressWarnings("unused")
	private static List<ProjectManage> sortMap(Map<Integer, ProjectManage> mapIn) {
		List<ProjectManage> list = new ArrayList<ProjectManage>();
		Map<Integer, ProjectManage> map = new TreeMap<Integer, ProjectManage>(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				return o1 - o2;
			}
		});
		map.putAll(mapIn);
		Set<Integer> keySet = map.keySet();
		Iterator<Integer> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			Integer next = iterator.next();
			ProjectManage value = map.get(next);
			if (null != value) {
				list.add(value);
			}

		}
		return list;

	}

	@SuppressWarnings("unchecked")
	@Override
	public PageResult<ProjectResponse> selectHotProjectPage(Integer userId, PaginationQuery query) throws RestServiceException {
		// TODO 获取最热项目排行榜(关注人数超过1000人的前20个项目；)
		PageResult<ProjectResponse> projectResponsePage = null;
		List<ProjectResponse> projectResponseList = new ArrayList<ProjectResponse>();
		List<Integer> projectFollow = null;
		if (null != userId) {
			projectFollow = (List<Integer>) kffFollowService.getUserFollow(userId, 1).get("followedProjectIds");
		}
		query.addQueryData("DYfollowerNum", "1000");
		query.addQueryData("status", "1");
		query.addQueryData("sortField", "follower_num");
		query.addQueryData("sortSequence", "DESC");
		query.addQueryData("sortFieldNext", "total_score");
		query.addQueryData("sortSequenceNext", "DESC");
		// query.setRowsPerPage(20);
		PageResult<KFFProject> projectPage = kffProjectService.findPage(query);
		if (null != projectPage && !CollectionUtils.isEmpty(projectPage.getRows())) {
			List<KFFProject> projectList = projectPage.getRows();
			for (KFFProject kffProject : projectList) {
				if (null != kffProject) {
					ProjectResponse projectResponse = new ProjectResponse();
					DozerMapperUtils.map(kffProject, projectResponse);
					if (null != projectResponse) {
						if (null != userId) {
							if (null != projectResponse.getProjectId() && null != userId && projectFollow.contains(projectResponse.getProjectId())) {
								projectResponse.setFollowStatus(1);
							} else {
								projectResponse.setFollowStatus(0);
							}
						}
					}
					projectResponseList.add(projectResponse);
				}
			}
		}
		projectResponsePage = new PageResult<ProjectResponse>(projectResponseList, projectPage.getRowCount(), query);
		return projectResponsePage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageResult<ProjectResponse> selectEvaProjectPage(Integer userId, PaginationQuery query) {
		// TODO 获得项目评分榜(评分人数超过10个，按项目评分来进行排名，最多20；)
		PageResult<ProjectResponse> projectResponsePage = null;
		List<ProjectResponse> projectResponseList = new ArrayList<ProjectResponse>();
		List<Integer> projectFollow = null;
		if (null != userId) {
			projectFollow = (List<Integer>) kffFollowService.getUserFollow(userId, 1).get("followedProjectIds");
		}
		query.addQueryData("DYtotalRaterNum", "10");
		query.addQueryData("status", "1");
		// query.setPageIndex(20);
		query.addQueryData("sortField", "total_score");
		query.addQueryData("sortSequence", "DESC");
		query.addQueryData("sortFieldNext", "follower_num");
		query.addQueryData("sortSequenceNext", "DESC");
		PageResult<KFFProject> projectPage = kffProjectService.findPage(query);
		if (null != projectPage && !CollectionUtils.isEmpty(projectPage.getRows())) {
			List<KFFProject> projectList = projectPage.getRows();
			for (KFFProject kffProject : projectList) {
				if (null != kffProject) {
					ProjectResponse projectResponse = new ProjectResponse();
					DozerMapperUtils.map(kffProject, projectResponse);
					if (null != projectResponse) {
						if (null != projectResponse.getProjectId() && null != userId && projectFollow.contains(projectResponse.getProjectId())) {
							projectResponse.setFollowStatus(1);
						} else {
							projectResponse.setFollowStatus(0);
						}
					}
					projectResponseList.add(projectResponse);
				}
			}
		}
		projectResponsePage = new PageResult<ProjectResponse>(projectResponseList, projectPage.getRowCount(), query);
		return projectResponsePage;
	}

	/**
	 * tabid 1 全部 2 关注
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageResult<ProjectResponse> showProjectList(Integer tabId, Integer userId, PaginationQuery query) {
		// TODO 根据tabId查询project分页展示
		Integer pageIndex = query.getPageIndex();
		Integer pageSize = query.getRowsPerPage();
		PageResult<ProjectResponse> projectResponsePage = null;
		List<Integer> projectFollow = null;
		List<Follow> followList = null;
		if (null != userId) {

			Map<String, Object> resultMap = kffFollowService.getUserFollow(userId, 1);
			projectFollow = (List<Integer>) resultMap.get("followedProjectIds");
			followList = (List<Follow>) resultMap.get("followList");
		}
		if (null == tabId) {
			throw new RestServiceException("tab栏参数出错");
		}

		if (tabId == 1) {
			// 查询全部项目
			PageResult<ProjectResponse> projectsPage = kffRmiService.findProjectByCodePage(1, userId, null, pageIndex, pageSize);
			// PageResult<ProjectResponse> projectsPage =
			// kffProjectService.findAllProjectAndTrade(query);
			if (null != projectsPage && null != projectsPage.getRows() && !CollectionUtils.isEmpty(projectsPage.getRows())) {
				List<ProjectResponse> projectList = projectsPage.getRows();

				for (ProjectResponse projectResponse : projectList) {
					if (null != projectResponse) {
						Map<String, String> map = new HashMap<String, String>();

						map.put("projectId", projectResponse.getProjectId() + "");
						List<ProjectTrade> projectTradeList = projectTradeService.findByMap(map);
						if (!CollectionUtils.isEmpty(projectTradeList)) {
							ProjectTrade projectTrade = projectTradeList.get(0);
							projectResponse.setPercentChange1h(projectTrade.getPercentChange1h());
							projectResponse.setPercentChange24h(projectTrade.getPercentChange24h());
							projectResponse.setPercentChange7d(projectTrade.getPercentChange7d());
							projectResponse.setPrice(projectTrade.getPrice());
							projectResponse.setMarketCap(projectTrade.getMarketCap());
							projectResponse.setVolume24h(projectTrade.getVolume24h());
							projectResponse.setRank(projectTrade.getRank());
						}
					}
				}

				if (null != projectList && !CollectionUtils.isEmpty(projectList)) {
					for (ProjectResponse projectResponse : projectList) {
						if (null != projectResponse && null != userId) {
							if (null != projectFollow && !CollectionUtils.isEmpty(projectFollow) && projectFollow.contains(projectResponse.getProjectId())) {
								projectResponse.setFollowStatus(1);// 设置关注
							} else {
								projectResponse.setFollowStatus(0);// 设置未关注
							}

						}
					}
					projectResponsePage = new PageResult<ProjectResponse>(projectList, projectsPage.getRowCount(), query);
				}
			}
		} else if (tabId == 2) {
			// 查询用户关注的project列表
			PaginationQuery queryFollow = new PaginationQuery();
			if (null != userId) {
				queryFollow.addQueryData("followUserId", userId + "");
				queryFollow.addQueryData("followType", 1 + "");
				queryFollow.addQueryData("status", "1");
				queryFollow.setPageIndex(pageIndex);
				queryFollow.setRowsPerPage(pageSize);

				List<ProjectResponse> projectResponseList = new ArrayList<ProjectResponse>();
				KFFUser loginUser = kffUserService.findById(userId);
				Integer type = 2;// 取关注人
				PageResult<FollowResponse> followPage = kffRmiService.findPageMyFollow(queryFollow, type, loginUser);

				if (null != followPage && null != followPage.getRows() && !CollectionUtils.isEmpty(followPage.getRows())) {
					List<FollowResponse> followResponseList = followPage.getRows();
					for (FollowResponse followResponse : followResponseList) {
						if (null != followResponse) {
							ProjectResponse projectResponse = new ProjectResponse();

							DozerMapperUtils.map(followResponse, projectResponse);
							projectResponse.setProjectId(followResponse.getFollowedProjectId());
							projectResponse.setFollowerNum(followResponse.getFollowerNum());
							Map<String, String> projectMap = new HashMap<String, String>();
							projectMap.put("projectId", followResponse.getFollowedProjectId() + "");
							List<ProjectTrade> projectTradeList = projectTradeService.findByMap(projectMap);
							if (!CollectionUtils.isEmpty(projectTradeList)) {
								ProjectTrade projectTrade = projectTradeList.get(0);
								projectResponse.setPercentChange1h(projectTrade.getPercentChange1h());
								projectResponse.setPercentChange24h(projectTrade.getPercentChange24h());
								projectResponse.setPercentChange7d(projectTrade.getPercentChange7d());
								projectResponse.setPrice(projectTrade.getPrice());
								projectResponse.setMarketCap(projectTrade.getMarketCap());
								projectResponse.setVolume24h(projectTrade.getVolume24h());
								projectResponse.setRank(projectTrade.getRank());

							}
							projectResponse.setFollowStatus(1);
							projectResponseList.add(projectResponse);
						}
					}
					projectResponsePage = new PageResult<ProjectResponse>(projectResponseList, followPage.getRowCount(), query);
				}
			}
		} else {
			// 除了关注和全部
			PaginationQuery queryOther = query;
			projectResponsePage = selectProjectsListByProjectCodePage(tabId, queryOther, userId, projectFollow);
		}
		// System.err.println("projectResponsePage" + JSON.toJSONString(projectResponsePage));
		return projectResponsePage;
	}

	/**
	 * tabid 1 全部 2 关注
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageResult<ProjectResponse> showProjectListNew(Integer tabId, Integer userId, PaginationQuery query) {
		// TODO 根据tabId查询project分页展示
		Integer pageIndex = query.getPageIndex();
		Integer pageSize = query.getRowsPerPage();
		PageResult<ProjectResponse> projectResponsePage = null;
		List<Integer> projectFollow = null;
		List<Follow> followList = null;
		if (null != userId) {
			Map<String, Object> resultMap = kffFollowService.getUserFollow(userId, 1);
			projectFollow = (List<Integer>) resultMap.get("followedProjectIds");
			followList = (List<Follow>) resultMap.get("followList");
		}
		if (null == tabId) {
			throw new RestServiceException("tab栏参数出错");
		}

		if (tabId == 1) {
			// 查询全部项目
			// PageResult<ProjectResponse> projectsPage = kffRmiService.findProjectByCodePage(1,
			// userId, null, pageIndex, pageSize);
			query.addQueryData("sortField", "follower_num");
			query.addQueryData("sortSequence", "desc");

			PageResult<ProjectResponse> projectsPage = kffProjectService.findAllProjectAndTrade(query);
			if (null != projectsPage && null != projectsPage.getRows() && !CollectionUtils.isEmpty(projectsPage.getRows())) {
				List<ProjectResponse> projectList = projectsPage.getRows();

				if (null != projectList && !CollectionUtils.isEmpty(projectList)) {
					for (ProjectResponse projectResponse : projectList) {
						if (null != projectResponse && null != userId) {
							if (null != projectFollow && !CollectionUtils.isEmpty(projectFollow) && projectFollow.contains(projectResponse.getProjectId())) {
								projectResponse.setFollowStatus(1);// 设置关注
							} else {
								projectResponse.setFollowStatus(0);// 设置未关注
							}

						}
					}
					projectResponsePage = new PageResult<ProjectResponse>(projectList, projectsPage.getRowCount(), query);
				}
			}
		} else if (tabId == 2 && userId != null && !(CollectionUtils.isEmpty(followList))) {
			// 查询用户关注的project列表
			query.addQueryData("sortField", "follower_num");
			query.addQueryData("sortSequence", "desc");
			String inList = StringUtils.join(projectFollow.toArray(), ",");
			System.err.println(inList);
			query.addQueryData("inList", projectFollow);
			PageResult<ProjectResponse> projectsPage = kffProjectService.findFansProjectAndTrade(query);
			System.err.println(JSON.toJSONString(projectsPage));
			if (null != projectsPage && null != projectsPage.getRows() && !CollectionUtils.isEmpty(projectsPage.getRows())) {
				List<ProjectResponse> projectResponseList = projectsPage.getRows();
				System.err.println(JSON.toJSONString(projectResponseList));
				for (ProjectResponse projectResponse : projectResponseList) {
					if (null != projectResponse) {
						projectResponse.setFollowStatus(1);

					}
				}

				projectResponsePage = new PageResult<ProjectResponse>(projectResponseList, projectsPage.getRowCount(), query);
			}

		} else {
			// 除了关注和全部
			PaginationQuery queryOther = query;
			projectResponsePage = selectProjectsListByProjectCodePageNew(tabId, queryOther, userId, projectFollow);
		}
		// System.err.println("projectResponsePage" + JSON.toJSONString(projectResponsePage));
		return projectResponsePage;
	}

	private PageResult<ProjectResponse> selectProjectsListByProjectCodePageNew(Integer tabId, PaginationQuery query, Integer userId, List<Integer> projectFollow) {
		// TODO 根据projectList查询project 并用分页进行展示
		PageResult<ProjectResponse> projectResponsePage = null;

		// List<Integer> followedProjectIds = new ArrayList<Integer>();
		List<Integer> projectIds = new ArrayList<Integer>();
		// 判断此项目是否被这个用户 关注

		if (null != tabId) {

			List<String> projectCodes = projectForTabService.selectProjectCodeByTabid(tabId);
			if (!CollectionUtils.isEmpty(projectCodes)) {
				if (projectCodes.size() != 0) {
					for (String projectCode : projectCodes) {

						if (StringUtils.isNotEmpty(projectCode)) {
							// 根据projectcode查询project对象
							KFFProject projectResponse = selectProjectByCode(projectCode, userId);
							// 将审核通过的项目放入队列中
							if (null != projectResponse && projectResponse.getState() == 2) {
								projectIds.add(projectResponse.getProjectId());
							}
						}
					}
				}
				System.err.println("projectIds" + JSON.toJSONString(projectIds));
				if (!CollectionUtils.isEmpty(projectIds)) {
					if (projectIds.size() == 1) {
						query.addQueryData("projectId", projectIds.get(0));
					} else {
						String projectIdListDB = StringUtils.join(projectIds.toArray(), ",");// 进入数据库的查询参数
						query.addQueryData("inList", projectIds);
					}
				}
			}
			PageResult<ProjectResponse> projectsPage = kffProjectService.findAllProjectAndTrade(query);
			if (null != projectsPage && null != projectsPage.getRows() && !CollectionUtils.isEmpty(projectsPage.getRows())) {
				List<ProjectResponse> projectList = projectsPage.getRows();

				if (null != projectList && !CollectionUtils.isEmpty(projectList)) {
					for (ProjectResponse projectResponse : projectList) {
						if (null != projectResponse && null != userId) {
							if (null != projectFollow && !CollectionUtils.isEmpty(projectFollow) && projectFollow.contains(projectResponse.getProjectId())) {
								projectResponse.setFollowStatus(1);// 设置关注
							} else {
								projectResponse.setFollowStatus(0);// 设置未关注
							}

						}
					}
					projectResponsePage = new PageResult<ProjectResponse>(projectList, projectsPage.getRowCount(), query);
				}
			}
		}
		return projectResponsePage;

	}

	@Override
	public PageResult<TransactionPairResponse> selectExchangeAndTranPair(Integer projectId, PaginationQuery query) throws RestServiceException {
		// TODO 根据projectId 查询相关的交易所和交易对
		PageResult<TransactionPairResponse> transactionPairResponsePage = null;
		List<TransactionPairResponse> transactionPairResponseList = new ArrayList<TransactionPairResponse>();
		if (null == projectId) {
			throw new RestServiceException("projectid不存在");
		}
		KFFProject project = kffProjectService.findById(projectId);
		if (null == project) {
			throw new RestServiceException("project不存在");

		}
		String projectCode = project.getProjectCode();
		if (StringUtils.isNotEmpty(projectCode)) {
			// 根据projectcode查询相关的交易对
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mainCode", projectCode);
			map.put("vaild", "1");
			map.put("sortField", "exchange_id");
			map.put("sortSequence", "ASC");
			query.setQueryData(map);
			PageResult<TransactionPair> transactionPairPage = transactionPairService.findPage(query);
			if (null != transactionPairPage && !CollectionUtils.isEmpty(transactionPairPage.getRows())) {
				List<TransactionPair> transactionPairList = transactionPairPage.getRows();
				if (!CollectionUtils.isEmpty(transactionPairList)) {
					for (TransactionPair transactionPair : transactionPairList) {
						TransactionPairResponse transactionPairResponse = new TransactionPairResponse();
						DozerMapperUtils.map(transactionPair, transactionPairResponse);
						transactionPairResponseList.add(transactionPairResponse);
					}
					transactionPairResponsePage = new PageResult<TransactionPairResponse>(transactionPairResponseList, transactionPairPage.getRowCount(), query);
				}
			}
		}
		return transactionPairResponsePage;
	}

	@Override
	public void text() throws RestServiceException {
		Date now = new Date();
		// TODO Auto-generated method stub
		/*PaginationQuery query = new PaginationQuery();
		query.setPageIndex(1);
		query.setRowsPerPage(10);
		PageResult<ProjectResponse> findAllProjectAndTrade = kffProjectService.findAllProjectAndTrade(query);*/
		// System.err.println(JSON.toJSONString(findAllProjectAndTrade));
		// transactionPairService.getdatafromUrlByexchangeTask();
		List<ProjectTrade> projectTradeList = new ArrayList<ProjectTrade>();
		for (int i = 1; i < 5; i++) {
			ProjectTrade projectTrade = new ProjectTrade();
			projectTrade.setProjectTradeId(i);
			projectTrade.setUpdataTime(now);
			projectTrade.setPrice(2.0);
			projectTradeList.add(projectTrade);
		}
		projectTradeService.updateBatch(projectTradeList);
	}
}
