package com.swen90014.paplatypusbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swen90014.paplatypusbackend.controller.utils.ChartInfoUtil;
import com.swen90014.paplatypusbackend.controller.utils.ChartUtil;
import com.swen90014.paplatypusbackend.controller.utils.ResultUtil;
import com.swen90014.paplatypusbackend.controller.utils.barChartUtil;
import com.swen90014.paplatypusbackend.service.impl.ChartServiceImpl;
import org.jfree.data.json.impl.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/chart")
public class ChartController {
    @Autowired
    ChartServiceImpl chartService;

    @GetMapping("/getBar/{subjectId}")
    public ResultUtil getAll(@PathVariable Long subjectId) throws RuntimeException, JsonProcessingException {
        BufferedImage bufferedImage = chartService.subjectChart(subjectId);
        // Convert BufferedImage to byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        String url="https://sm.ms/api/v2/upload";
        MultiValueMap<String, Object> paraMap = new LinkedMultiValueMap<>();
        ByteArrayResource contentsAsResource = new ByteArrayResource(imageBytes) {
            @Override
            public String getFilename() {
                return "04.png";
            }
        };

        paraMap.add("smfile", contentsAsResource);


        RestTemplate restTemplate = new RestTemplate();
        // Create HttpHeaders
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("multipart/form-data"));
        headers.set("Authorization", "iEY2YI6YYCwcyJ2BFDoC0G8LR6tzdufh");

        // Create HttpEntity
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(paraMap, headers);

        JSONObject response = restTemplate.postForObject(url, requestEntity, JSONObject.class);


        assert response != null;
        if ((Boolean) response.get("success")) {
            // Parse the JSON response to get the URL
            // You can use Jackson or any other JSON parsing library here
            // Assuming 'response' is the JSONObject containing your API response
            JSONObject dataObject = (JSONObject) response.get("data"); // Get the 'data' object from the response
            String imageUrl = (String)dataObject.get("url"); // Get the 'url' field from the 'data' object


            return new ResultUtil(200, imageUrl, "SUCCESS");
        } else {
            throw new RuntimeException("Failed to upload image: " + response.get("code"));
        }
    }
    


    @GetMapping("/getPie/{subjectId}/{studentId}")
    public ResultUtil getOne(@PathVariable Long subjectId, @PathVariable Long studentId){
        BufferedImage bufferedImage = chartService.studentChart(subjectId,studentId);
        String currentWorkingDirectory = System.getProperty("user.dir");
        File outputfile = new File(currentWorkingDirectory + "pa_backend/src/main/resources/Image/PieChart.png");
        try {
            ImageIO.write(bufferedImage, "png", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResultUtil(200, outputfile.getAbsolutePath(), "SUCCESS");

    }

    @GetMapping("/getBarData/{subjectId}")
    public ResultUtil getBarData(@PathVariable Long subjectId){
        barChartUtil chartData = chartService.barData(subjectId);
        return new ResultUtil(200, chartData, "SUCCESS");
    }

    @GetMapping("/getPieData/{subjectId}/{studentId}")
    public ResultUtil getPieData(@PathVariable Long subjectId, @PathVariable Long studentId){
        ChartUtil chartUtil = chartService.pieData(subjectId, studentId);
        ChartInfoUtil chartInfoUtil = new ChartInfoUtil();
        chartInfoUtil.setDiagramName("Student Individual Participation Pie Chart");
        chartInfoUtil.setDiagramData(chartUtil);
        return new ResultUtil(200, chartInfoUtil, "SUCCESS");
    }

}
