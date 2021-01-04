package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.statics;

public class Urls {
    public static final String CHILDREN = "/**";

    public static final String RESOURCES_FOLDER = "/resources";
    public static final String STATIC_FOLDER = "/static";
    public static final String WEBJARS_FOLDER = "/webjars";
    public static final String CSS_FOLDER = "/css";
    public static final String JS_FOLDER = "/js";
    public static final String IMAGES_FOLDER = "/images";
    public static final String TEMPLATES_FOLDER = "/templates";

    public static final String ERROR_NOT_FOUND = "/error/not_found";
    public static final String ERROR_UNAUTHORIZED = "/error/unauthorized";

    public static final String RESOURCES_FOLDER_ACCESS_ALL = RESOURCES_FOLDER + CHILDREN;
    public static final String STATIC_FOLDER_ACCESS_ALL = STATIC_FOLDER + CHILDREN;
    public static final String WEBJARS_FOLDER_ACCESS_ALL = WEBJARS_FOLDER + CHILDREN;
    public static final String CSS_FOLDER_ACCESS_ALL = CSS_FOLDER + CHILDREN;
    public static final String JS_FOLDER_ACCESS_ALL = JS_FOLDER + CHILDREN;
    public static final String IMAGES_FOLDER_ACCESS_ALL = IMAGES_FOLDER + CHILDREN;
    public static final String TEMPLATES_FOLDER_ACCESS_ALL = TEMPLATES_FOLDER + CHILDREN;

    public static final String HOME_PAGE = "/";
    public static final String REGISTRATION_PAGE = "/registration";
    public static final String LOGIN_PAGE = "/login";
    public static final String LOGOUT_PAGE = "/logout";

    public static final String USER_PROFILE_PAGE = "/user/profile";

    private static final String HOME = "/home";
    private static final String NEW = "/new";
    private static final String SAVE = "/save";
    private static final String DETAILS = "/details";
    private static final String EDIT = "/edit";
    private static final String SAVE_UPDATE = "/saveUpdate";
    private static final String DELETE=  "/delete";

    public static final String HOME_HOME_PAGE = HOME;

    public static final String JOBS_LIST_PAGE = "/jobsList";
    public static final String JOBS_LIST_PAGE_ACCESS_ALL = JOBS_LIST_PAGE + CHILDREN;
    public static final String JOBS_LIST_HOME_PAGE = JOBS_LIST_PAGE + HOME;
    public static final String JOBS_LIST_NEW_PAGE = JOBS_LIST_PAGE + NEW;
    public static final String JOBS_LIST_SAVE_PAGE = JOBS_LIST_PAGE + SAVE;
    public static final String JOBS_LIST_DETAILS_PAGE = JOBS_LIST_PAGE + DETAILS;
    public static final String JOBS_LIST_EDIT_PAGE = JOBS_LIST_PAGE + EDIT;
    public static final String JOBS_LIST_SAVE_UPDATE_PAGE = JOBS_LIST_PAGE + SAVE_UPDATE;
    public static final String JOBS_LIST_DELETE_PAGE = JOBS_LIST_PAGE + DELETE;

    public static final String JOB_PAGE = "/job";
    public static final String JOBS_PAGE_ACCESS_ALL = JOB_PAGE + CHILDREN;
    public static final String JOB_HOME_PAGE = JOB_PAGE + HOME;
    public static final String JOB_NEW_PAGE = JOB_PAGE + NEW;
    public static final String JOB_SAVE_PAGE = JOB_PAGE + SAVE;
    public static final String JOB_DETAILS_PAGE = JOB_PAGE + DETAILS;
    public static final String JOB_EDIT_PAGE = JOB_PAGE + EDIT;
    public static final String JOB_SAVE_UPDATE_PAGE = JOB_PAGE + SAVE_UPDATE;
    public static final String JOB_DELETE_PAGE = JOB_PAGE + DELETE;
}
