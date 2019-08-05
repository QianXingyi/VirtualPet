package cn.moecity.virtualpet;

public class User {
private int uID;
private String uName;
private String uPhone;
private String uPass;
private int uMoney;
private String phoneID;
public User() {
}
public User(int uID, String uName, String uPhone, String uPass, int uMoney,String phoneID) {
	super();
	this.uID = uID;
	this.uName = uName;
	this.uPhone = uPhone;
	this.uPass = MD5.generatePassword(uPass);
	this.uMoney = uMoney;
	this.phoneID=phoneID;
}
public int getuID() {
	return uID;
}
public void setuID(int uID) {
	this.uID = uID;
}
public String getuName() {
	return uName;
}
public void setuName(String uName) {
	this.uName = uName;
}
public String getuPhone() {
	return uPhone;
}
public void setuPhone(String uPhone) {
	this.uPhone = uPhone;
}
public int getuMoney() {
	return uMoney;
}
public void setuMoney(int uMoney) {
	this.uMoney = uMoney;
}
public String getuPass() {
	return uPass;
}
public void setuPass(String uPass) {
	this.uPass = MD5.generatePassword(uPass);
}

	public String getPhoneID() {
		return phoneID;
	}

	public void setPhoneID(String phoneID) {
		this.phoneID = phoneID;
	}

	@Override
	public String toString() {
		return "User{" +
				"uID=" + uID +
				", uName='" + uName + '\'' +
				", uPhone='" + uPhone + '\'' +
				", uPass='" + uPass + '\'' +
				", uMoney=" + uMoney +
				", phoneID='" + phoneID + '\'' +
				'}';
	}

}
