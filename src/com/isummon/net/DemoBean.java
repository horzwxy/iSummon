package com.isummon.net;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Hashtable;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
public class DemoBean implements KvmSerializable{
    private int id;
    private String name;
    public DemoBean() {
    }
    public DemoBean(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public static DemoBean deserialize(byte[] data) {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        DemoBean demoBean = new DemoBean();
        try {
            demoBean.setId(dis.readInt());
            demoBean.setName(dis.readUTF());
        } catch (NumberFormatException e) {
        } catch (IOException e) {
        } finally {
            try {
                if (bais != null)
                    bais.close();
                if (dis != null)
                    dis.close();
            } catch (IOException e) {
            }
        }
        return demoBean;
    }
    @Override
    public Object getProperty(int arg0) {
        switch (arg0) {
            case 0:
                return this.id;
            case 1:
                return this.name;
            default:
                break;
        }
        return null;
    }
    @Override
    public int getPropertyCount() {
        return 2;
    }
    @Override
    public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
        switch (arg0) {
            case 0:
                arg2.type = PropertyInfo.INTEGER_CLASS;
                arg2.name = "id";
                break;
            case 1:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name = "name";
                break;
            default:
                break;
        }
    }
    @Override
    public void setProperty(int arg0, Object arg1) {
        switch (arg0) {
            case 0 :
                id = Integer.valueOf(arg1.toString());
                break;
            case 1 :
                name = arg1.toString();
                break;
            default:
                break;
        }
    }
}