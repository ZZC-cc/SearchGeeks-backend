package com.cc.ccso;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cc.ccso.model.entity.Picture;
import com.cc.ccso.model.entity.Post;
import com.cc.ccso.service.PostService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class CrawlerTest {
    @Resource
    private PostService postService;
    @Test
    void testFetchPicture() throws IOException {
        int currentPage = 1;
        String keyWord = "ikun小黑子";
        String url = "https://www.bing.com/images/search?q="+keyWord+"&first="+currentPage;
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select(".iuscp.isv");
        List<Picture> pictures = new ArrayList<>();
        for (Element element : elements) {
            //取图片地址（murl)
            String m = element.select(".iusc").get(0).attr("m");
            Map<String,Object> map = JSONUtil.toBean(m, Map.class);
            String murl = (String) map.get("murl");
//            System.out.println(murl);
            //获取标题
            String title = element.select(".inflnk").get(0).attr("aria-label");
//            System.out.println(title);
            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setUrl(murl);
            pictures.add(picture);
        }
        System.out.println(pictures);
    }

    @Test
    void testFetchPassages(){
        //1.获取数据
        String json = "{\n" +
                "  \"current\": 1,\n" +
                "  \"pageSize\": 8,\n" +
                "  \"sortField\": \"createTime\",\n" +
                "  \"sortOrder\": \"descend\",\n" +
                "  \"category\": \"文章\",\n" +
                "  \"reviewStatus\": 1\n" +
                "}";
        String url = "https://www.code-nav.cn/api/post/search/page/vo";
        String result = HttpRequest
                .post(url)
                .body(json)
                .execute()
                .body();
//        System.out.println(result);
        //2.json转对象
        Map<String,Object> map = JSONUtil.toBean(result, Map.class);

        JSONObject data = (JSONObject) map.get("data");
        JSONArray records = (JSONArray) data.get("records");
        List<Post> postList = new ArrayList<>();
        for (Object record : records) {
            JSONObject temp = (JSONObject) record;
            Post post = new Post();
            post.setTitle(temp.getStr("title"));
            post.setContent(temp.getStr("content"));
            JSONArray tags = (JSONArray) temp.get("tags");
            List<String> tagList = tags.toList(String.class);
            post.setTags(JSONUtil.toJsonStr(tagList));
            post.setUserId(1L);
            postList.add(post);
        }
//        System.out.println(postList);
        //3.数据入库
        boolean b = postService.saveBatch(postList);
        Assertions.assertTrue(b);
    }
}
