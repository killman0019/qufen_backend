package com.tzg.entitys.leopard.person;

import java.io.Serializable;

import com.tzg.entitys.BaseModel;

/**
 * Created by cfour on 12/1/14.
 */
public class PersonalRole extends BaseModel implements Serializable{
    /**
	* @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = 1L;
	private int iConsoleLoginAccountID;
    private int iRoleinfoID;

    public int getiConsoleLoginAccountID() {
        return iConsoleLoginAccountID;
    }

    public void setiConsoleLoginAccountID(int iConsoleLoginAccountID) {
        this.iConsoleLoginAccountID = iConsoleLoginAccountID;
    }

    public int getiRoleinfoID() {
        return iRoleinfoID;
    }

    public void setiRoleinfoID(int iRoleinfoID) {
        this.iRoleinfoID = iRoleinfoID;
    }
}
