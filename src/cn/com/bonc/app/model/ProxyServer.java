package cn.com.bonc.app.model;


public class ProxyServer {
      String ip;
      String port;
      String type;
      String area;
      String modifyTime;
      String validDayCount;
      String sourceSite;
      String sourcePage;
      String isValid;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getValidDayCount() {
		return validDayCount;
	}
	public void setValidDayCount(String validDayCount) {
		this.validDayCount = validDayCount;
	}
	public String getSourceSite() {
		return sourceSite;
	}
	public void setSourceSite(String sourceSite) {
		this.sourceSite = sourceSite;
	}
	public String getSourcePage() {
		return sourcePage;
	}
	public void setSourcePage(String sourcePage) {
		this.sourcePage = sourcePage;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	
	@Override
	public boolean equals(Object obj) {
        if(obj == null) return false;
        if(this == obj) return true;
        if(obj instanceof ProxyServer){ 
        	ProxyServer user =(ProxyServer)obj;
            if(user.port.equals(this.port)&& user.ip.equals(this.ip)) 
             return true;
            }
        return false;
    } 
  
    @Override
    public int hashCode() {
//      return id.hashCode(); // ֻ�Ƚ�id��idһ���Ͳ���ӽ�����
        return port.hashCode() * ip.hashCode();
    }
  
}

