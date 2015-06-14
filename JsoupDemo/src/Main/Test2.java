package Main;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Util.HtmlUtil;

//这个页面是最新复制的页面  内容与test.java一样
public class Test2 
{
	public static int level = 0;
//	jsoup + encodeaaa
	public static void main1(String[] args) throws UnsupportedEncodingException 
	{
		String root_html = HtmlUtil.getHtml("http://www.baike.com");
		Document doc=Jsoup.parse(root_html);
		
		Elements first_class=doc.select(".classify>ul>li");  //获得所有一级类所在的li
		for(Element fc:first_class)
		{ 
			Element first_class_name=fc.select("h3>a").first();  //获取一级类类别名称
			System.out.println("一级类："+first_class_name.text());
			Elements second_class=fc.select("p>a");  //获取2级目录
			for(Element sc:second_class)
			{
				getDirStructure(sc.text().trim());
				
			}
			
		}
		
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		getDirStructure("艺术理论 ");
	}
	
	public static void getDirStructure(String parent_name) throws UnsupportedEncodingException{
		String parent_href = "http://fenlei.baike.com/" + URLEncoder.encode(parent_name,"utf-8");
		String parent_html = HtmlUtil.getHtml(parent_href);
		Document doc=Jsoup.parse(parent_html);
		Elements sub_class_p = doc.select(".sort_all.up p");
		Elements sub_class_p2 = doc.select(".sort p");
		Elements sub_class_p_a_list = null;
		String this_suojin = "";
		if(sub_class_p.size() > 1 && level < 3){
			level ++;
			for(int i=0;i<level;i++){
				this_suojin += "\t\t";
				
			}
			sub_class_p_a_list = sub_class_p.eq(1).select("a");
			for(Element sub_class_p_a : sub_class_p_a_list){
				System.out.println("下一级类:"+this_suojin+sub_class_p_a.text().trim());
				getDirStructure(sub_class_p_a.text().trim());
				
			}
			
			level --;
		}else{
			if(sub_class_p2.size() > 1  && level < 3){
				level ++;
				for(int i=0;i<level;i++){
					this_suojin += "\t\t";
					
				}
				sub_class_p_a_list = sub_class_p2.eq(1).select("a");
				for(Element sub_class_p_a : sub_class_p_a_list){
					System.out.println("下一级类:"+this_suojin+sub_class_p_a.text().trim());
					
					getDirStructure(sub_class_p_a.text().trim());
					
				}
				
				level --;
				
			}else{
				if(sub_class_p2.size() <= 1 && sub_class_p.size() <= 1){
//					http://fenlei.baike.com/%E7%BE%8E%E6%9C%AF%E5%8F%B2/?prd=fenleishequ_zifenlei
					
				}
				
				
				
			}
		}	
		
	}
	
	
}
