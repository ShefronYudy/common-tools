package com.shefron.test;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

public class HtmlTemplateV2 {
	private Date theDate = new Date(System.currentTimeMillis()+0*(24*60*60*1000) );
	private String yearStr = String.valueOf(15);
	private String prefix = new SimpleDateFormat("dd").format(theDate);
	private String monthStr = new SimpleDateFormat("MM").format(theDate);
	private String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(theDate);
	
	private List<String> summarysList = new ArrayList<String>();
	private List<String> imgDescList = new ArrayList<String>();
	
	private List<String> titleList = new ArrayList<String>();
	private List<String> describeList = new ArrayList<String>();
	
	public static void main(String [] args){
		HtmlTemplateV2 newsTemplate =  new HtmlTemplateV2();
		try{
			newsTemplate.createFiles();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public  void createFiles() {
//        yearStr = "14";
//        prefix = "13";
//        monthStr = "10";
//        dateStr = "2014-10-13";

		prefix = Integer.valueOf(prefix)+"_";
		monthStr = String.valueOf(Integer.valueOf(monthStr));
		
		String keywords = "苏克杰";
		String author = "中广艺术";
		int size = 14;
		//图片描述
		loadImgDesc();
		//加載標題與描述
		loadTitleAndDescribe();
		//段落叙述
		summarysList.add(getSummary("（中国广电艺术网讯）苏克杰,1968年6月参军入伍,1987年3月转业国家计委办公厅。现国家发改委基建管理中心任副主任。中国民族建筑研究会民族建筑环境与居住文化专业委员会副主任委员，中央国家机关书法家协会会员。受苏氏家庭传承，自幼喜爱书法，通过对书法自我悟性的提高，目前已经达到了书法界知名度极高的认知度。"));
        summarysList.add(getSummary("其书法作品无论是楷书、隶书、行草那种静穆庄严、清逸朗然，从书艺上可以给人一种如沐春风之感；从境界上，其超然之情，了无挂碍；卓然之志，清静神逸，能在寄情物外、浑然忘我中游艺而怡然自得。"));
        summarysList.add(getSummary(""));

		try {
			createHtmls(titleList,keywords,describeList,author,imgDescList,summarysList,size);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadTitleAndDescribe() {
		URL url = HtmlTemplateV2.class.getResource("/resource/title_describe.out");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(url.getPath()), "UTF-8"));
			String line = null;
			int i=1;
			while ((line = reader.readLine()) != null) {
				if(i%2==0){
					describeList.add(line);
				}else{
					titleList.add(line);
				}
				i++;
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("titleList.size:"+titleList.size()+";describeList.size:"+describeList.size());
	}

	public void loadImgDesc(){
		URL url = HtmlTemplateV2.class.getResource("/resource/imgDesc.out");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(url.getPath()), "UTF-8"));
			String line = null;
			int i=1;
			while ((line = reader.readLine()) != null) {
				if(i%2==0){
					imgDescList.add(line);
				}
				i++;
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("size:"+imgDescList.size()+";"+imgDescList);
	}
	
	public void createHtmls(List<String> title,String keywords,List<String> describe,String author,List<String> altStr, List<String> summarys, int size) throws Exception {
		String divBegin = "<DIV id=\"chan_multipageNumN\">\n";
		
		String prev = "\t<A class=\"firstPage\" title=\"首页\" href=\""+prefix+"1.html\" target=\"_self\">首页</A>\n"
				+ "\t<A class=\"prevPage\" title=\"到上一页\" href=\""+prefix+"&prevPage&.html\" target=\"_self\">上页</A>\n";
		
		String page = "\t<SPAN>...</SPAN>\n"
				+ "\t<A href=\""+prefix+"&prev2&.html\" target=\"_self\">&prev2&</A>\n"
				+ "\t<A href=\""+prefix+"&prev1&.html\" target=\"_self\">&prev1&</A>\n"
				+ "\t<SPAN class=\"curPage\">&currPage&</SPAN>\n"
				+ "\t<A href=\""+prefix+"&next1&.html\" target=\"_self\">&next1&</A>\n"
				+ "\t<A href=\""+prefix+"&next2&.html\" target=\"_self\">&next2&</A>\n"
				+ "\t<SPAN>...</SPAN>\n";
		
		String next = "\t<A class=\"nextPage\" title=\"到下一页\" href=\"./"+prefix+"&nextPage&.html\" target=\"_self\">下页</A>\n"
				+ "\t<A class=\"lastPage\" title=\"到最后一页\" href=\"./"+prefix+size+".html\" target=\"_self\">尾页</A>\n";
		
		String divEnd = "\t<SPAN class=\"sumPage\">共<CITE>"+size+"</CITE>页</SPAN>\n"
				+ "</DIV>\n";
		

		StringBuffer div2 = new StringBuffer();
		div2.append(divBegin);
		div2.append(prev);
		div2.append(page);
		div2.append(next);
		div2.append(divEnd);
		
		StringBuffer div = null;
		
		String path = "D:/server/www/res/"+yearStr+"/"+monthStr+"/"+prefix+"&Page&.html";
		File file = null;
		
		String pages = null;
		for (int i = 1; i <= size; i++) {
			if (i<=3){
				div = new StringBuffer();
				div.append(divBegin);
				div.append(getPageNums(i) );
				div.append(next);
				div.append(divEnd);
			}else	if (i>(size-3) ){
				div = new StringBuffer();
				div.append(divBegin);
				div.append(prev);
				div.append(getTailPageNums(i,size));
				div.append(divEnd);
			}else{
				div = div2;
			}
			
			
			pages = div.toString().replace("&prevPage&", "" + (i - 1))
					.replace("&nextPage&", "" + (i + 1))
					.replace("&currPage&", "" + i)
					.replace("&prev2&", "" + (i - 2))
					.replace("&prev1&", "" + (i - 1))
					.replace("&next1&", "" + (i + 1))
					.replace("&next2&", "" + (i + 2));

			file = new File(path.replace("&Page&", "" + i));
			localFileAppend(file,getAll(i,size,pages,( i>altStr.size() ? altStr.get(altStr.size()-1):altStr.get(i-1)),title,keywords,describe,author, ( i>summarys.size() ? summarys.get(summarys.size()-1):summarys.get(i-1)) ));
			Thread.sleep(100);
			System.out
					.println("----------------------------------------------------------------------------------");
		}
	}
	
	public String getAll(int seq,int size, String pages, String altStr,List<String> title,String keywords,List<String> describe,String author,String summary) throws Exception{
		StringBuffer buffer = new StringBuffer();
		buffer.append(getHeadContent(seq,title, keywords,describe,author));
		String widths = getImgWidth(seq);
		buffer.append(getTable(seq,size, widths.split(",")[0], widths.split(",")[1], altStr));
		buffer.append(getMiddleContent(summary) );
		buffer.append(pages);
		buffer.append(solidString());
		return buffer.toString();
	}

	public String solidString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n   </DIV>\n");
		buffer.append("\n   <span style=\"text-indent:2.5em;padding:0px;line-height:100%;font-size:12px;letter-spacing:1px;font-weight:bold;\">直接点击图片即可翻页</span>\n");
		buffer.append("\t</div>\n</div>\n");
		buffer.append("<div style=\"width:970px;display:block;\">\n");
		buffer.append("<div><SCRIPT language=JavaScript src=\"../../js/zhuanti-copyright.js\"></SCRIPT></div>\n");
		buffer.append("<div><script type=\"text/javascript\">var cnzz_protocol = ((\"https:\" == document.location.protocol) ? \" https://\" : \" http://\");document.write(unescape(\"%3Cspan id='cnzz_stat_icon_4627067'%3E%3C/span%3E%3Cscript src='\" + cnzz_protocol + \"s84.cnzz.com/stat.php%3Fid%3D4627067%26show%3Dpic' type='text/javascript'%3E%3C/script%3E\"));</script></div>\n");
		buffer.append("</div>\n");
		buffer.append("</center>\n");
		buffer.append("</body>\n");
		buffer.append("</html>\n");

		return buffer.toString();
	}

	private String getSummary(String summary){
		return "	<p style=\"width:900px;letter-spacing:2pt;line-height:30px;font-family:'宋体';font-size:16px;text-indent:2em;text-align:center;\">"+summary+"</p>\n";
	}

	public String getPageNums(int curPage){
		StringBuffer buffer = new StringBuffer();
		for (int i=1;i<6;i++){
			if (curPage == i){
				buffer.append("\t<SPAN class=\"curPage\">"+curPage+"</SPAN>\n");
			}else{
				buffer.append("\t<A href=\""+prefix+i+".html\" target=\"_self\">"+i+"</A>\n");
			}
		}
		buffer.append("\t<SPAN>...</SPAN>\n");

		return buffer.toString();
	}

	public String getTailPageNums(int curPage,int tailNum){
		StringBuffer buffer = new StringBuffer();
		buffer.append("\t<SPAN>...</SPAN>\n");
		int i = tailNum-4;
		for (;i<=tailNum;i++){
			if (curPage == i){
				buffer.append("\t<SPAN class=\"curPage\">"+curPage+"</SPAN>\n");
			}else{
				buffer.append("\t<A href=\""+prefix+i+".html\" target=\"_self\">"+i+"</A>\n");
			}
		}

		return buffer.toString();
	}

	public String getImgWidth(int seq) throws IOException{
		String filePath = "D:/server/www/res/images/"+yearStr+"/"+monthStr+"/"+prefix+seq+".jpg";
		System.out.println("filePath:"+filePath);
		File file = new File(filePath);
		BufferedImage image = ImageIO.read(file);
		int imgWidth = image.getWidth();
		//图片宽度若大于800则采用滚动方式显示
		int tdWidth = (970-imgWidth)/2;
		if(imgWidth > 800){
			tdWidth = (970 -800)/2;
		}
		return imgWidth+","+tdWidth;
	}

	


	public String getHeadContent(int seq,List<String> title, String keywords,List<String> describe,String author) {
		URL url = HtmlTemplateV2.class.getResource("/resource/head.out");
		StringBuffer buffer = new StringBuffer();

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(url.getPath()), "GBK"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line + "\n");
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		buffer.append("\n");

		return buffer.toString().replace("@@title@@", seq>title.size()?title.get(title.size()-1):title.get(seq-1)).replace("@@author@@", author)
				.replace("@@keywords@@", keywords).replace("@@date@@", dateStr).replace("@@describe@@", seq>describe.size()?describe.get(describe.size()-1):describe.get(seq-1));
	}
	
	public String getTable(int seq,int size, String imgWidth, String leftWidth,String altStr) {
		/**
		 * 	<div>
		<div id="imgMain">
		    <a href="./0731_2.html" target="_self"><img id="imgAltShow" src="../images/201407/0731_1.jpg" alt="释延瑫法师" title="释延瑫法师" width="500px" border="0"/></a>		    
		</div>
		<div id="imgDesc" style="font-size:14px;letter-spacing:4px;">释延瑫法师</div>
	</div>
		 */
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("\t<div style=\"width:970px;display:block;border-left:1px solid #ccc;border-right:1px solid #ccc;border-top:2px solid #666666;border-bottom:2px solid #666666;\">\n");
		buffer.append("\n\t\t<div>&nbsp;</div>\n");
		buffer.append("\t\t<div id=\"imgWidth\" style=\"display:block;border:1px solid #D0D0D3;overflow:hidden;overflow-y:hidden;\" onmouseout=\"hiddenScroll();\" onmouseover=\"showScroll();\">\n");
		buffer.append("\t\t\t<a href=\"./"+prefix+( (seq + 1)>size ? 1:(seq+1))+".html\" target=\"_self\"><img id=\"imgAltShow\" onload=\"scrollImgDiv();\" src=\"../../images/"+yearStr+"/"+monthStr+"/"+prefix+seq+".jpg\" alt=\""+altStr+" 中国广电艺术网\" title=\""+altStr+" 中国广电艺术网\" width=\""+imgWidth+"px\" border=\"0\"/></a>\n");
		buffer.append("\t\t\t<script type=\"text/javascript\" language=\"javascript\">/*SCROLL BIG IMAGE*/var imgLen=$(\"#imgAltShow\").width()-800;var scrollFlag=imgLen>0?true:false;var firstFlag=false;function hiddenScroll(){$(\"#imgWidth\").css({\"overflow\":\"hidden\"})}function showScroll(){$(\"#imgWidth\").css({\"overflow\":\"auto\"})}function scrollImgDiv(){if(!scrollFlag){$(\"#imgWidth\").css({\"width\":$(\"#imgAltShow\").width()});return}if(scrollFlag&&!firstFlag){firstFlag=true;$(\"#imgWidth\").css({\"overflow\":\"auto\",\"width\":\"800px\"})}imgLen=imgLen-2;if(imgLen<2){return}var divObj=$(\"#imgWidth\").scrollLeft(imgLen);setTimeout(scrollImgDiv,50)};</script>");
		buffer.append("\t\t</div>\n");
		buffer.append("\t\t<div id=\"imgDesc\" style=\"font-size:14px;letter-spacing:4px;\">"+altStr+"</div>\n");
		buffer.append("\t<script>window._bd_share_config={\"common\":{\"bdSnsKey\":{},\"bdText\":\"\",\"bdMini\":\"2\",\"bdMiniList\":false,\"bdPic\":\"\",\"bdStyle\":\"0\",\"bdSize\":\"16\"},\"slide\":{\"type\":\"slide\",\"bdImg\":\"1\",\"bdPos\":\"right\",\"bdTop\":\"100\"},\"selectShare\":{\"bdContainerClass\":null,\"bdSelectMiniList\":[\"qzone\",\"tsina\",\"tqq\",\"renren\",\"weixin\"]}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>\n");
		
		return buffer.toString();
	}
	
	public String getMiddleContent(String summary) {
		return summary+"	<DIV class=\"pageStyle\">\n";
	}

	public void localFileAppend(File file, String content) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file, false), "UTF-8"));
			out.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
