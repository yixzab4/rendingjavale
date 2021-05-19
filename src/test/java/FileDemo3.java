import java.io.File;

	public class FileDemo3 {

		public static void main(String[] args) {
			File dir=new File("C:\\Users\\Administrator\\AppData\\Local\\Temp");
			//File dir=new File("F:\\");
			//如果使用上述的盘符的根目录，会出现java.lang.NullPointerException
			//为什么？
			getAllFiles(dir,0);//0表示最顶层
		}
		//获取层级的方法
		public static String getLevel(int level)
		{
			//A mutable sequence of characters.
			StringBuilder sb=new StringBuilder();
			for(int l=0;l<level;l++)
			{
				sb.append("|--");
			}
			return sb.toString();
		}
		public static void getAllFiles(File dir,int level)
		{
			System.out.println(getLevel(level)+dir.getName());
			level++;
			File[] files=dir.listFiles();
			if(files.length==0){
				dir.delete();
			}
			for(int i=0;i<files.length;i++)
			{
				if(files[i].isDirectory())
				{
					if(files[i].listFiles().length==0){
						files[i].delete();
						continue;
					}
					//这里面用了递归的算法
					getAllFiles(files[i],level);
				}
				else {
					System.out.println(getLevel(level)+files[i]);
					String path = files[i].getPath();
					String houzhui = path.substring(path.lastIndexOf("."));
					if(".txt".equals(houzhui)){
						files[i].delete();
						getAllFiles(files[i].getParentFile(), level--);
					}
					
				}	
			}		
			
		}
	}
