package org.shefron.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.sql.Driver;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Assert;
import org.junit.Test;
import org.shefron.fc.utfwithbom.UTFFileHandler;
import org.shefron.utils.FileCharsetDetector;
import org.shefron.utils.XmlPrintWriter;

/**
 *@RunWith(*Runner.class),测试化类型
 *@RunWith(Suite.class):打包测试化类
 * @author a
 *
 */
public class TestMain {

	/**
	 * //标注@Test(timeout = 1000|expected =
	 * ArithmeticException.class),@Ignore,@Before[](Fixture), //
	 * @After(Fixture)固定代码块,@Parameters(参数化测试时，定义参数集合)
	 *
	 * @param args
	 * @throws Exception
	 */

	public void testNum() {
		String str = "-111";
		String pattern = "(\\-[0-9]+\\.?[0-9]+)|([0]+\\.[0-9]+)";// 小于1

		System.out.println(matcher(str, pattern));

	}

	public void testPureChinese() {
		String str = "日本人";
		String pattern = "[\u4e00-\u9fa5]+";// 纯中文

		System.out.println("TEST:" + matcher(str, pattern));
	}

	public void testNumber() {
		String str = "-1236589545";
		String pattern = "(\\-[0-9]+\\.?[0-9]+)|0|([0-9]+\\.?[0-9]+)";// 负数,0,正数

		System.out.println(matcher(str, pattern));
	}

	public void testNoPositiveNum() {
		String str = "00";
		String pattern = ".*[\\D]+.*|0*";// 非正整数

		System.out.println(matcher(str, pattern));
	}

	public void testStringFormat() {
		System.out.println(String.format("Where is my %2$s %3$d %4$,.2f",
				"watch ?", "hehe ?", 123, 2233.34d));

	}

	public void testSetContains() {
		Set<String> vars = new HashSet<String>();
		Assert
				.assertTrue(!(!vars.contains("OM-02-04-009-00") && vars.size() > 0));
	}

	public void testIntern() {
		String resourceIntern = "resource".intern();
		String resource = "resource";
		Assert.assertTrue(resource.equals(resourceIntern));
	}

	public void testAtomicInteger() {
		AtomicInteger in = new AtomicInteger();
		System.out.println(in.addAndGet(4));
		in.getAndIncrement();
		System.out.println(in.get());
	}

	public void testGuid() {
		//号称全球唯一ID:32位
		for (int i = 1; i < 4; i++) {
			System.out.println("uuid:" + UUID.randomUUID());
		}
	}

	public void testNanoSeconds() {
		// 测试纳秒
		System.out.println(System.currentTimeMillis());
		System.out.println(System.nanoTime());
	}

	public void testArrayCopy() {
		String[] orgArray = new String[] { "1", "2", "3", "4" };
		String[] values = new String[orgArray.length];

		System.arraycopy(orgArray, 0, values, 0, orgArray.length);

		for (String item : values) {
			System.out.println("item:" + item);
		}
	}

	public void testJavaPath() {
		final String classpath = System.getProperty("java.class.path");
		final String pathSeparator = System.getProperty("path.separator");
		System.out.println(classpath + "\n" + pathSeparator);
	}

	public void testValueof() {
		System.out.println("###############" + Double.valueOf("  0"));
		System.out.println("###############" + Math.pow(4, 2));
		double isNAN = 01231221212121212121212222.0;
		System.out.println("isNaN:" + Double.isNaN(isNAN));

		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		System.out.println(df.format(new Date()));

		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(1970, 0, 1, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);

		System.out.println(cal.getTimeInMillis());
	}

	private boolean matcher(String str, String pattern) {
		if (str == null) {
			return false;
		}
		Pattern _pattern = Pattern.compile(pattern);
		Matcher matcher = _pattern.matcher(str);
		boolean b = matcher.matches();
		return b;
	}

	public String getFileContent(String filePath, String encode)
			throws Exception {
		StringBuffer buffer = new StringBuffer();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(filePath), encode));
		String line = null;
		while ((line = reader.readLine()) != null) {
			buffer.append(line + "\n");
		}
		return buffer.toString();
	}


	public void formatXml(String filePath) {
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(new File(filePath));
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			XMLWriter writer = new XMLWriter(new FileOutputStream(new File(
					filePath)), format);
			writer.write(doc);
			writer.flush();
			writer.close();
		} catch (Exception e) {
		}
	}

	public void testMessageFormat() {
		String msg = "hello world,{0} how are you? {1}";
		System.out.println(MessageFormat.format(msg, new Object[] { "XiaoMing",
				"XiaoFeng" }));
		System.out.println("#" + new String(new byte[] { 10 }) + "#");

		System.out.println("#" + (int) ' ' + "#");
		msg.charAt(0);

		System.out.println("int.class:" + int.class);
		System.out.println("Integer.class:" + Integer.class);
	}

	public void testArray() {
		// 一维数组
		Object arrayObj = Array.newInstance(Integer.class, 3);
		Array.set(arrayObj, 0, new Integer(1));
		System.out.println(Array.get(arrayObj, 0));
		// 二位数组
		Object arrayObj2 = Array.newInstance(Integer[].class, 3);
		Array.set(arrayObj2, 0, arrayObj);
		System.out.println(Array.get(Array.get(arrayObj2, 0), 0));

		StringBuffer buffer = new StringBuffer();
		buffer.append("hello world");
		System.out.println(buffer.toString());
		buffer.setLength(0);
		System.out.println(buffer.toString());
		buffer.append("HELLO");
		System.out.println(buffer.toString());

		System.out.println("HW_EPS_ZZZZZZ_012345"
				.compareTo("AP_APS_ZZZZZZ_123456"));
	}

	public void testUTFFileWithBom() {
		String filePath = "E:\\temp\\res\\SMMWQ_371_01DY_20140825_000_000.xml";

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					UTFFileHandler.getInputStreamWithoutBom(filePath, null),
					"UTF-8"));

			StringBuffer buffer = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line + "\n");
			}
			System.out.println(buffer.toString());

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void testCharset() {
		try {
			FileCharsetDetector.CharsetObj charsetObj = FileCharsetDetector
					.getPriorityCharset("E:/temp/res/SMMWQ_371_01DY_20140825_000_000.xml");
			System.out.println(charsetObj);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testGetDrivers(){
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()){
			System.out.println(drivers.nextElement().getClass().getName());

		}
	}

//	@Test
	public void testClearBlankWithinStr(){
		String content = "   1 .docx";
		print(content.replaceAll("\\s{1,}|\t|\r|\n", ""));
	}

	private void println(String msg) {
		System.out.println("#"+msg+"#");
	}

	private void print(String msg) {
		System.out.print("#"+msg+"#");
	}

	public void testStringTokenizer(){
		StringTokenizer st = new StringTokenizer("hello,kks;ks,llkkk|123", ",;|");
		while (st.hasMoreTokens()) {
			println(st.nextToken());
		}

	}

	public void testSplitStr(){
		String str = "zh_CN_kskks";
		for (String item : str.split("_", 2))
			System.out.println("item:"+item);
	}

//	@Test
	public void testCollection(){
		List<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("111");

		List<String> viewList = Collections.unmodifiableList(list);

		//视图只读列表不允许修改
//		viewList.add("222");

		System.out.println(viewList);


		Set<String> set = new HashSet<String>();
		set.add("ccc");
		set.add("333");

		Set<String> viewSet = (Set)Collections.unmodifiableSet(set);
		//视图只读集合不允许修改
//		viewSet.add("444");

		System.out.println(viewSet);

		SortedMap<String,String> map = new TreeMap<String,String>(new Comparator(){
			@Override
			public int compare(Object o1, Object o2) {
				if(o1.equals(o2)){
					return 0;
				}

				return o1.toString().compareToIgnoreCase(o2.toString());
			}

		});

		map.put("bbb", "111");
		map.put("aaa", "222");

		SortedMap viewMap = Collections.unmodifiableSortedMap(map);

		System.out.println(map);

	}

//	@Test
	public void testPrintHtml(){
		StringWriter str = new StringWriter();


		XmlPrintWriter printWriter = new XmlPrintWriter(str,new char[]{' ',' '},"\n");


		printWriter.startNode("table");
		printWriter.addAttribute("id", "table_id");
		printWriter.addAttribute("name", "table_name");

		printWriter.startNode("tr");


		printWriter.startNode("td");
		printWriter.setValue("<null&");
		printWriter.endNode();

		printWriter.endNode();

		printWriter.endNode();


		System.out.println(str.toString());

	}

//	@Test
	public void testMillsFormat(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date = new Date(1413145912931l);
		System.out.println(sdf.format(date) );
	}

	  /**
     * 返回排序后的文件列表
     * @param files 需要排序的文件集合
     * @param ascFlag true:升序 false:降序
     * @return
     */
    public List<File> getSortedFilesByTime(Collection<File> files, final boolean ascFlag){
        List<File> sortedFiles = new ArrayList<File>();
        for(File file : files){
            sortedFiles.add(file);
        }

        Collections.sort(sortedFiles, new Comparator<File>() {
            public int compare(File o1, File o2) {
                long diff = o1.lastModified() - o2.lastModified();

                int i = 0;
                if(diff>0){
                    i = ascFlag ? 1:-1;

                }else if(diff == 0){
                    i = 0;
                }else{
                    i = ascFlag ? -1:1;
                }
                return i;
            }
        });

        return sortedFiles;

    }
	
	@Test
	public void testSplit(){
		File dir = new File("E:/res/temp");
		File [] files = dir.listFiles();
		String name = "";
		File newFile = null;
		for (File file: files){
			name = file.getName();
			newFile = new File(file.getPath().substring(0, file.getPath().lastIndexOf("\\")) + 
						name.replace("", "")+
						name.replace("", "") );
			
			System.out.println("old file "+file+" -> new file "+newFile);
			file.renameTo(newFile);
		}
		
	}


}
