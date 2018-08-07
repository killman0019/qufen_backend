package com.tzg.common.service.kff;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tzg.common.utils.DateUtil;
import com.tzg.common.utils.HttpUtil;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.entitys.kff.project.KFFProjectMapper;
import com.tzg.entitys.kff.projecttrade.ProjectTrade;
import com.tzg.entitys.kff.projecttrade.ProjectTradeMapper;

@Service(value = "ProjectTradeService")
@Transactional
public class ProjectTradeService {
	@Autowired
	private ProjectTradeMapper projectTradeMapper;

	@Autowired
	private KFFProjectMapper kffProjectMapper;

	/**
	 * 
	 * TODO 进行定时获取project相关详情数据
	 * @author zhangdd
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 * @data 2018年8月6日
	 *
	 */
	public void getProjectDateFromUrlTask() {
		// 获得项目表中的项目列表

		try {
			List<ProjectTrade> projectTradeListInsert = new ArrayList<ProjectTrade>();
			List<ProjectTrade> projectTradeListUpdate = new ArrayList<ProjectTrade>();
			Date now = new Date();

			String strBase = getToken();

			String doGetData = HttpUtil.doGet(strBase);
			System.err.println("doGetData" + doGetData);
			JSONObject jsonObject = new JSONObject(doGetData);
			System.err.println("jsonObject" + jsonObject);
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			if (null != jsonArray && jsonArray.length() > 0) {

				for (Object object : jsonArray) {
					JSONObject jsonData = new JSONObject(object.toString());
					// String name = jsonData.getString("name");
					int cmcId = jsonData.getInt("id");
					int rank = jsonData.getInt("cmc_rank");
					Object objectQuote = jsonData.get("quote");
					if (null != objectQuote) {
						JSONObject objectQuoteData = new JSONObject(objectQuote.toString());
						Object objectCNY = objectQuoteData.get("CNY");
						JSONObject objectCNYData = new JSONObject(objectCNY.toString());
						double volume24h = objectCNYData.getDouble("volume_24h");
						double percentChange1h = objectCNYData.getDouble("percent_change_1h");
						double percentChange24h = objectCNYData.getDouble("percent_change_24h");
						double percentChange7d = objectCNYData.getDouble("percent_change_7d");
						double marketCap = objectCNYData.getDouble("market_cap");
						double price = objectCNYData.getDouble("price");
						Map<String, String> map = new HashMap<String, String>();
						map.put("cmcId", cmcId + "");
						List<ProjectTrade> projectTradeList = projectTradeMapper.findByMap(map);
						ProjectTrade projectTrade = new ProjectTrade();
						projectTrade.setCmcId(cmcId);
						projectTrade.setMarketCap(marketCap);
						projectTrade.setPercentChange1h(percentChange1h);
						projectTrade.setPercentChange24h(percentChange24h);
						projectTrade.setPercentChange7d(percentChange7d);
						projectTrade.setPrice(price);
						projectTrade.setRank(rank);
						projectTrade.setUpdataTime(now);
						projectTrade.setVolume24h(volume24h);
						if (CollectionUtils.isEmpty(projectTradeList)) {
							// 插入新添加数据
							Map<String, Object> projectMap = new HashMap<String, Object>();
							projectMap.put("cmcId", cmcId);
							List<KFFProject> projectList = kffProjectMapper.findByMap(projectMap);
							if (!CollectionUtils.isEmpty(projectList)) {
								if (null != projectList.get(0)) {
									projectTrade.setProjectId(projectList.get(0).getProjectId());
									projectTrade.setCreateTime(now);
									projectTrade.setStatus(1);
									projectTradeMapper.save(projectTrade);
								}
							}

						} else if (!CollectionUtils.isEmpty(projectTradeList)) {
							// 进行更新
							if (projectTradeList.get(0) != null) {
								ProjectTrade projectTradeForDB = projectTradeList.get(0);
								if (null != projectTradeForDB) {
									Integer projectTradeId = projectTradeForDB.getProjectTradeId();
									projectTrade.setUpdataTime(now);
									projectTrade.setProjectTradeId(projectTradeId);
									projectTradeMapper.update(projectTrade);
									/*projectTradeListUpdate.add(projectTrade);
									if (projectTradeListUpdate.size() == 100) {
										projectTradeMapper.updateBatch(projectTradeListUpdate);
										projectTradeListUpdate.clear();
									}*/
								}
							}

						}
					}
				}

				// projectTradeMapper.updateBatch(projectTradeListUpdate);
			} else {
				System.err.println("error null");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getToken() {
		String str = null;

		List<String> strList = new ArrayList<String>();
		strList.add("50d37f65-148f-4dcc-af8a-ce89b66520d6");
		strList.add("7cd95529-50c0-45fe-99d8-afda95263ae1");
		strList.add("55f3b9b5-ac69-41a5-854d-f090db55a329");
		strList.add("ce3ee81a-495b-4392-897d-89d7bce1f9bb");
		strList.add("4ae583b4-541e-4a4c-ab1f-380fc59852ae");
		strList.add("e9372d64-d99a-49a0-834a-04547f7dcbe9");
		strList.add("e6a9ddf6-b30b-443a-8fa7-d78fea278779");
		strList.add("c916d453-b500-45ed-8b26-ded7b7cdadfb");
		strList.add("aef1250c-609a-4b6a-8358-92de9fe817a1");
		strList.add("c6834445-048f-4737-8703-064e63d5e662");
		strList.add("408357a1-105d-46c0-8dd2-3acd1f977ec9");
		strList.add("ffc4a351-b594-4e60-8033-85eeb217fdcc");
		strList.add("825c34ae-cf0f-4832-b774-90733392ffc0");
		strList.add("81832d80-3119-4a20-8053-e14415516417");
		strList.add("9fd772ee-1180-4979-b58f-c8932c0df388");

		/*strList.add("e6a9ddf6-b30b-443a-8fa7-d78fea278779");
		strList.add("c916d453-b500-45ed-8b26-ded7b7cdadfb");
		strList.add("aef1250c-609a-4b6a-8358-92de9fe817a1");
		
		strList.add("c6834445-048f-4737-8703-064e63d5e662");
		strList.add("408357a1-105d-46c0-8dd2-3acd1f977ec9");
		strList.add("ffc4a351-b594-4e60-8033-85eeb217fdcc");
		strList.add("825c34ae-cf0f-4832-b774-90733392ffc0");
		strList.add("81832d80-3119-4a20-8053-e14415516417");
		strList.add("9fd772ee-1180-4979-b58f-c8932c0df388");*/

		int hour = DateUtil.getHour();
		int minute = DateUtil.getMinute();
		hour = 23;
		minute = 50;
		int i = (hour * 60 + minute) / 150;
		System.err.println(i);
		str = strList.get(i);
		String strBase = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=" + str + "&convert=CNY&&limit=5000";
		return strBase;
	}

	public List<ProjectTrade> findByMap(Map<String, String> map) {
		// TODO Auto-generated method stub
		return projectTradeMapper.findByMap(map);
	}

	/**
	 * 
	 * TODO 
	 * @param projectTradeList
	 * @author zhangdd
	 * @data 2018年8月7日
	 *
	 */
	public void updateBatch(List<ProjectTrade> projectTradeList) {
		// TODO Auto-generated method stub
		projectTradeMapper.updateBatch(projectTradeList);
	}

	/*public static void main(String[] args) {
		System.err.println(getToken());
	}*/
}
