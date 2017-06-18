package cn.cellcom.wechat.po;
// default package



/**
 * City entity. @author MyEclipse Persistence Tools
 */
public class TCity implements java.io.Serializable {
	// Fields    

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
    private String name;
    private String postcode;
    private Long parentId;
    private String pinyinm;
    private String pinyinc;
    private String stringId;
  
    
   // Property accessors
    
   public Long getId() {
       return this.id;
   }
   
   public void setId(Long id) {
       this.id = id;
   }

   public String getName() {
       return this.name;
   }
   
   public void setName(String name) {
       this.name = name;
   }

   public String getPostcode() {
       return this.postcode;
   }
   
   public void setPostcode(String postcode) {
       this.postcode = postcode;
   }
   
   public Long getParentId()
	{
		return parentId;
	}

	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}
	
	public String getPinyinm()
	{
		return pinyinm;
	}

	public void setPinyinm(String pinyinm)
	{
		this.pinyinm = pinyinm;
	}

	public String getPinyinc()
	{
		return pinyinc;
	}

	public void setPinyinc(String pinyinc)
	{
		this.pinyinc = pinyinc;
	}
	
	public String getStringId(){
		if (this.id != null) {
			return this.id.toString();
		} else {
			return "";
		}
	}
	
	public void setStringId(){
		
	}
	
	// Constructors

	/** default constructor */
    public TCity() {
    }
 
}
