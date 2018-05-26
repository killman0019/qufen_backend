package com.tzg.action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.RandomUtil;
import com.tzg.common.utils.TzgConstant;
import com.tzg.core.utils.DateUtil;
import com.tzg.entitys.uploadfile.Uploadfile;
import com.tzg.service.uploadfile.UploadfileService;

/**
 * 
 * @Description：上传资料
 * @author
 * @Date 2015-1-7
 * 
 */
@Controller
@RequestMapping("/uploadfile")
public class UploadfileController extends BaseController {
	private static Logger log = Logger.getLogger(UploadfileController.class);

	@Autowired
	private UploadfileService uploadfileService;

	@RequestMapping(value = "/new")
	public String add(RedirectAttributes redirectAttributes) throws Exception {
		try {
			return "/uploadfile/new";
		} catch (Exception e) {
			log.error(e);
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_EXCEPTION);
			return "redirect:/uploadfile";
		}
	}

	@RequestMapping(value = "/detail")
	public String detail(Integer id, Model model) {
		try {
			Uploadfile uploadfile = uploadfileService.findById(id);
			model.addAttribute("uploadfile", uploadfile);
			model.addAttribute(SUCCESS, true);
			return "/uploadfile/detail";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/uploadfile/detail";
		}
	}

	@RequestMapping(value = "/find")
	public String find(Integer id, Model model) {
		try {
			Uploadfile uploadfile = uploadfileService.findById(id);
			model.addAttribute("uploadfile", uploadfile);
			model.addAttribute(SUCCESS, true);
			return "/uploadfile/update";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/uploadfile/update";
		}
	}

	@RequestMapping(value = "/save")
	public String save(@ModelAttribute("uploadfile") Uploadfile uploadfile,
			Model model, @RequestParam("uploadfile") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		try {
			if (file.isEmpty()) {
				throw new Exception("未选择需要上传的图标！");
			}
			uploadfile.setVcOrnName(file.getOriginalFilename());
			String url = createUploadFileName(file.getOriginalFilename());
			uploadfile.setVcUrl(url);
			uploadfile.setBlobSource(file.getBytes());
			uploadfileService.save(uploadfile);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_SAVE_SUCESS);
			return "redirect:/uploadfile";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("uploadfile", uploadfile);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/uploadfile/new";
		}
	}

	@RequestMapping(value = "/update")
	public String update(@ModelAttribute("uploadfile") Uploadfile uploadfile,
			Model model, @RequestParam("uploadfile") MultipartFile file,
			RedirectAttributes redirectAttributes) throws Exception {
		try {
			//String url = null;
			if (!file.isEmpty()) {
				uploadfile.setVcOrnName(file.getOriginalFilename());
				//url = createUploadFileName(file.getOriginalFilename());
				//uploadfile.setVcUrl(url);
				uploadfile.setBlobSource(file.getBytes());
			}
			uploadfileService.update(uploadfile);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_UPDATE_SUCESS);
			return "redirect:/uploadfile";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("uploadfile", uploadfile);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/uploadfile/update";
		}
	}

	@RequestMapping(value = "/delete")
	public String delete(Integer id, Model model) throws Exception {
		try {
			uploadfileService.delete(id);
			model.addAttribute(SUCCESS, true);
			return "redirect:/uploadfile";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/uploadfile";
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query, Model model, String itype,
			String vcName, String istate) throws Exception {
		try {
			query.addQueryData("itype", itype);
			query.addQueryData("istate", istate);
			query.addQueryData("vcName", vcName);
			PageResult<Uploadfile> pageList = uploadfileService.findPage(query);
			model.addAttribute("result", pageList);
			model.addAttribute("query", query.getQueryData());
			return "/uploadfile/list";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/uploadfile/list";
		}
	}

	private String createUploadFileName(String oriFileName) {
		String suffix = oriFileName.substring(oriFileName.lastIndexOf("."));
		return "/" + DateUtil.getCurrentYearMonth() + "/"
				+ DateUtil.getCurrentTime() + RandomUtil.produceNumber(6)
				+ suffix;
	}

	/**
	 * 分页列表
	 * 
	 * @param pageIndex
	 * @param iState
	 * @param vcName
	 * @param model
	 * @return
	 */
	@RequestMapping("/filePathList")
	public String filePathList(PaginationQuery query, Model model, String itype) {
		try {
			query.addQueryData("itype", itype);
			PageResult<Uploadfile> pageList = uploadfileService.findPage(query);
			model.addAttribute("result", pageList);
			return "/uploadfile/filepath_list";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/uploadfile/filepath_list";
		}
	}

	/**
	 * 
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/filePathDetail")
	public String filePathDetail(Integer id, Model model) {
		try {
			Uploadfile uploadfile = uploadfileService.findById(id);
			model.addAttribute("uploadfile", uploadfile);
			model.addAttribute(SUCCESS, true);
			return "/uploadfile/filepath_detail";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/uploadfile/filepath_detail";
		}
	}

	/**
	 * 上传所有未同步文件
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sync")
	public String sync(Model model, RedirectAttributes redirectAttributes) {
		try {
			uploadfileService.syncUpload();
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_OPERATE_SUCESS);
			return "redirect:/uploadfile";
		} catch (Exception e) {
			log.error(e);
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE, e.getMessage());
			return "redirect:/uploadfile";
		}
	}

}
