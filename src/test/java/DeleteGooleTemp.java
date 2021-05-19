import java.io.File;
import java.util.Calendar;

public class DeleteGooleTemp {
	public static void main(String[] args) {
		File dir=new File("C:\\Users\\Administrator\\AppData\\Local\\Temp");
		//File dir=new File("F:\\");
		//如果使用上述的盘符的根目录，会出现java.lang.NullPointerException
		//为什么？
		File[] files = dir.listFiles();
		int m = 1;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		for (int i = 0; i < files.length; i++) {
			String fileName = files[i].getName();
			if(fileName.contains("scoped_dir")){
				System.out.println(files[i].getName());
				long a = files[i].lastModified();
				System.out.println(a+"\t"+cal.getTimeInMillis()+"\t"+(cal.getTimeInMillis()>a));
				if(cal.getTimeInMillis()>a){
				deleteForder(files[i]);
				if(files[i]!=null&&files[i].exists()){
					if(files[i].listFiles().length==0){
						files[i].delete();
						if(!files[i].exists()){
							System.out.println(fileName+" has been delete,had delete "+m+" folder");
							m++;
						}
					}
				}else{
					System.out.println(fileName+" has been delete,had delete "+m+" folder");
					m++;
				}
				}
			}
		}
	}
		public static void deleteForder(File file){
			if(file!=null){
			File[] files = file.listFiles();
			if(files.length==0){
				file.delete();
			}else{
				for (int i = 0; i < files.length; i++) {
					if(files[i].isDirectory()){
						if(files[i].listFiles().length==0){
							files[i].delete();
						}
						if(files[i]!=null&&files[i].exists()){
							deleteForder(files[i]);
						}
						
					}else{
						if(files[i]!=null&&files[i].exists()){
							files[i].delete();
							deleteForder(files[i].getParentFile());
						}
					}
				}
			}
		}	
		}
}
