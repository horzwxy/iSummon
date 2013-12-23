package com.isummon.model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by hankzhang on 13-12-23.
 */
public class InvitationList extends Vector<Invitation> implements KvmSerializable {
    public  static final long serialVersionUID = -324324431433343L;
    private Vector<Invitation> inviteList;

    public InvitationList(Vector<Invitation> inviteList) {
        this.inviteList = inviteList;
    }
    @Override
    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return this.size();
    }


    @Override
    public void setProperty(int arg0, Object arg1) {
        // TODO Auto-generated method stub
        this.add((Invitation)arg1);
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        propertyInfo.name = "invitation";
        propertyInfo.type = Invitation.class;
    }

    @Override
    public Object getProperty(int i) {
        return this.get(i);
    }

}
