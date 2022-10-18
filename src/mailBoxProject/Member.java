package mailBoxProject;

public class Member {
	private int id;
	
	private int classNumber;
	
	private String className;
	
	private int classOfMailbox;
	
	private String mailboxName;
	
	private String address;
	
	private String descripAddress;
	
	private String mechanism;
	
	private String phoneNumber;
	
	private String remark;
	
	private float longitude;
	
	private float latitude;

	public int getClassNumber() {
		return classNumber;
	}	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setClassNumber(int classNumber) {
		this.classNumber = classNumber;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getClassOfMailbox() {
		return classOfMailbox;
	}

	public void setClassOfMailbox(int classOfMailbox) {
		this.classOfMailbox = classOfMailbox;
	}

	public String getMailboxName() {
		return mailboxName;
	}

	public void setMailboxName(String mailboxName) {
		this.mailboxName = mailboxName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescripAddress() {
		return descripAddress;
	}

	public void setDescripAddress(String descripAddress) {
		this.descripAddress = descripAddress;
	}

	public String getMechanism() {
		return mechanism;
	}

	public void setMechanism(String mechanism) {
		this.mechanism = mechanism;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public Member() {
	}

	public Member(int classNumber, String className, int classOfMailbox, String mailboxName, String address,
			String descripAddress, String mechanism, String phoneNumber, String remark, float longitude,
			float latitude) {
		this.classNumber = classNumber;
		this.className = className;
		this.classOfMailbox = classOfMailbox;
		this.mailboxName = mailboxName;
		this.address = address;
		this.descripAddress = descripAddress;
		this.mechanism = mechanism;
		this.phoneNumber = phoneNumber;
		this.remark = remark;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	
	
	
	
}
