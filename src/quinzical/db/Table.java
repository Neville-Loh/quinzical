package quinzical.db;

public enum Table {
		
	
		USER;
	
		public enum USER {
			ID("sadfads"),
			USERNAME("adsfj");
			
			
			private final String name;
			USER(String text) {
				this.name = text;
			}
			public String toString() {
				return name;
			}
		}
		
		enum CATEGORY{
			
		}
		enum QUESTOIN{
			
		}
		enum SESSION{
			
		}
		enum SESSIONQUESTION{
			
		}
		
		

}
