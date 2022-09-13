package com.vsii.utils;

import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;
import com.vsii.config.PropertiesConfig;

import javax.security.auth.Subject;

public class FilenetUtils {

    public static ObjectStore getObjectStore(PropertiesConfig prop) {
        Connection con = Factory.Connection.getConnection(prop.getUri());
        Subject sub = UserContext.createSubject(con, prop.getUserName(), prop.getPassWord(), prop.getStanza());
        UserContext.get().pushSubject(sub);
        Domain dom = Factory.Domain.getInstance(con, null);
        return Factory.ObjectStore.fetchInstance(dom, prop.getOsName(), null);
    }

}
