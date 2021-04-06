package com.ogarose.popugjira.common.messaging;

/**
 * Message topic convention
 * <data-center>.<domain>.<classification>.<description>.<version>
 */
public class MessageTopics {
    private final static String DOMAIN_AUTH = "auth";
    private final static String DOMAIN_TRACKER = "tracker";
    private final static String DOMAIN_ACCOUNTING = "tracker";

    private final static String CLASSIFICATION_BIZ = "biz";
    private final static String CLASSIFICATION_CUD = "cud";

    public final static String USER_BIZ = DOMAIN_AUTH + "." + CLASSIFICATION_BIZ + ".user";
    public final static String USER_CUD = DOMAIN_AUTH + "." + CLASSIFICATION_CUD + ".user";

    public final static String TASK_BIZ = DOMAIN_TRACKER + "." + CLASSIFICATION_BIZ + ".task";
    public final static String TASK_CUD = DOMAIN_TRACKER + "." + CLASSIFICATION_CUD + ".task";

    public final static String USER_ACCOUNTING_BIZ = DOMAIN_ACCOUNTING + "." + CLASSIFICATION_BIZ + ".user";
}
