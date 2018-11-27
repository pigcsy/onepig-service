package com.one.pig.common.exception;

/**
 * @Description 所有业务异常的枚举
 * @author csy
 * @date 2016年11月12日 下午5:04:51
 */
public enum BizExceptionEnum {

	/**
	 * 字典
	 */
	DICT_EXISTED(400, "字典已经存在"),
	ERROR_CREATE_DICT(500, "创建字典失败"),
	ERROR_WRAPPER_FIELD(500, "包装字典属性失败"),


	/**
	 * 文件上传
	 */
	FILE_READING_ERROR(400, "FILE_READING_ERROR!"),
	FILE_NOT_FOUND(400, "FILE_NOT_FOUND!"),
	UPLOAD_ERROR(500, "上传图片出错"),

	/**
	 * 权限和数据问题
	 */
	DB_RESOURCE_NULL(400, "数据库中没有该资源"),
	NO_PERMITION(405, "权限异常"),
	REQUEST_INVALIDATE(400, "请求数据格式不正确"),
	INVALID_KAPTCHA(400, "验证码已过期或验证码不正确"),
	DELETE_NULL(600, "用户id不能为null"),
	CANT_DELETE_ADMIN(600, "不能删除超级管理员"),
	CANT_FREEZE_ADMIN(600, "不能冻结超级管理员"),
	CANT_CHANGE_ADMIN(600, "不能修改超级管理员角色"),

	/**
	 * 账户问题
	 */
	USER_ALREADY_REG(401, "该用户已经注册"),
	EDIT_USER_ALREADY_REG(401, "修改的账户已存在"),
	NO_THIS_USER(200, "没有此用户"),
	HAD_THIS_USER(200, "账号已存在"),
	ACCOUNT_OR_PASSWORD_ERR(400, "账户名或密码错误"),
	ACCOUNT_FREEZED(401, "账号被冻结"),
	OLD_PWD_NOT_RIGHT(402, "原密码不正确"),
	TWO_PWD_NOT_MATCH(405, "两次输入密码不一致"),
	LOGIN_TIME_OUT(405, "登陆过期"),
	NEED_LOGIN(401, "请登录"),
	NO_POWER_GET(405, "无权访问"),
	LOGIN_FAIL(405, "授权失败"),

	/**
	 * 错误的请求
	 */
	MENU_PCODE_COINCIDENCE(400, "菜单编号和副编号不能一致"),
	EXISTED_THE_MENU(400, "菜单编号重复，不能添加"),
	DICT_MUST_BE_NUMBER(400, "字典的值必须为数字"),
	REQUEST_NULL(400, "请求有错误"),
	SESSION_TIMEOUT(400, "会话超时"),
	INPUT_ERR(400, "请求参数错误"),
	POST_OR_GET_ERR(400, "请求参数错误"),
	SERVER_ERROR(500, "服务器异常"),


	STITCHING_EXCEL_ERROR(500, "导出Excel报表headerMapping和dataMap的大小不匹配"),
	STREAM_EXCEL_ERROR(500, "生成报表输出流异常"),
	EXCEL_ERROR(500, "生成报表异常"),
	EXCEL_FAIL(500, "导出报表失败"),
	EXCEL_HEADER_NULL(500, "报表头信息为null"),
	EXCEL_DATA_NULL(500, "报表空值导致设置单元格长度异常"),

	SEND_EMAIL_ERROR(500, "缓存参数错误"),
	SEND_SERVICE_ERROR(500, "请求接口失败"),
	EXCEL_FORMAT_ERROR(500, "报表空值导致设置单元格长度异常"),
	READIS_ERROR(500, "缓存参数错误"),
	/**
	 * 业务数据异常
	 */
	NO_WHITE_IP(200, "ip白名单不存在，请检查网络"),
	NO_NEW_ORDER(200, "无最新订单，请稍后获取");

	BizExceptionEnum(int code, String message) {
		this.friendlyCode = code;
		this.friendlyMsg = message;
	}
	
	BizExceptionEnum(int code, String message,String urlPath) {
		this.friendlyCode = code;
		this.friendlyMsg = message;
		this.urlPath = urlPath;
	}

	private int friendlyCode;

	private String friendlyMsg;
	
	private String urlPath;

	public int getCode() {
		return friendlyCode;
	}

	public void setCode(int code) {
		this.friendlyCode = code;
	}

	public String getMessage() {
		return friendlyMsg;
	}

	public void setMessage(String message) {
		this.friendlyMsg = message;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

}
