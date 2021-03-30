package com.ogarose.popugjira.auth.service.message;

/**
 * Message topic convention
 * <data-center>.<domain>.<classification>.<description>.<version>
 */
public class EventTopics {
    private final static String DOMAIN = "auth";
    private final static String CLASSIFICATION_BIZ = "biz";
    private final static String CLASSIFICATION_CUD = "cud";

    public final static String USER_BIZ = DOMAIN + "." + CLASSIFICATION_BIZ + ".user";
    public final static String USER_CUD = DOMAIN + "." + CLASSIFICATION_CUD + ".user";
}
