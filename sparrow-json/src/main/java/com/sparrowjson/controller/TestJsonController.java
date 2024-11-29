//package com.sparrowjson.controller;
//
///**
// * Description:
// * Company    : 上海黄豆网络科技有限公司
// *
// * @author : hll
// * Date       : 2024/11/7
// * Modify     : 修改日期          修改人员        修改说明          JIRA编号
// * v1.0.0       2024/11/7       hll    新增              1001
// ********************************************************************/
//
//import com.sparrowjson.database.DatabaseMetaDataUtil;
//import com.sparrowjson.dto.SparrowBackendConfigDTO;
//import com.sparrowjson.mapper.SparrowBackendConfigMapper;
//import com.sparrowjson.util.ConfigFileReader;
//import com.sparrowjson.vo.ColumnVO;
//import com.sparrowjson.vo.MenuConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.*;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/test1Json")
//public class TestJsonController {
//
//    @Autowired
//    private SparrowBackendConfigMapper sparrowBackendConfigMapper;
//
//
//    @PostMapping("/v1FilePath")
//    public String v1FilePath(@RequestBody Map<String, Object> data) {
//        List<SparrowBackendConfigDTO> addList = sparrowBackendConfigMapper.listByTemplateAlias("add");
//        List<SparrowBackendConfigDTO> updateList = sparrowBackendConfigMapper.listByTemplateAlias("update");
//        List<SparrowBackendConfigDTO> deleteList = sparrowBackendConfigMapper.listByTemplateAlias("delete");
//        List<SparrowBackendConfigDTO> queryList = sparrowBackendConfigMapper.listByTemplateAlias("list");
//        String filePath = (String) data.get("file");
//        ConfigFileReader reader = new ConfigFileReader();
//        MenuConfig config = reader.readConfigFile(filePath);
//
//        Test test = new Test();
//        test.setAddList(addList);
//        test.setDeleteList(deleteList);
//        test.setQueryList(queryList);
//        test.setUpdateList(updateList);
//
//        List<ColumnVO> coulumns = DatabaseMetaDataUtil.getCoulumns(config.getTable());
//        config.setCoulumns(coulumns);
//
//        return test.test(config);
//    }
//
//    @PostMapping("/v2File")
//    public String v2File(@RequestParam("file") MultipartFile multipartFile) throws IOException {
//        List<SparrowBackendConfigDTO> addList = sparrowBackendConfigMapper.listByTemplateAlias("add");
//        List<SparrowBackendConfigDTO> updateList = sparrowBackendConfigMapper.listByTemplateAlias("update");
//        List<SparrowBackendConfigDTO> deleteList = sparrowBackendConfigMapper.listByTemplateAlias("delete");
//        List<SparrowBackendConfigDTO> queryList = sparrowBackendConfigMapper.listByTemplateAlias("list");
//        ConfigFileReader reader = new ConfigFileReader();
//        // 方法1：使用临时文件
//        File file = File.createTempFile("temp_", "_" + multipartFile.getOriginalFilename());
//        multipartFile.transferTo(file);
//        MenuConfig config = reader.readConfigFile(file);
//
//        Test test = new Test();
//        test.setAddList(addList);
//        test.setDeleteList(deleteList);
//        test.setQueryList(queryList);
//        test.setUpdateList(updateList);
//
//        List<ColumnVO> coulumns = DatabaseMetaDataUtil.getCoulumns(config.getTable());
//        config.setCoulumns(coulumns);
//
//        return test.test(config);
//    }
//}
