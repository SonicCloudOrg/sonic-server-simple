package org.cloud.sonic.simple.controller;

import org.bytedeco.javacv.FrameRecorder;
import org.cloud.sonic.simple.config.WebAspect;
import org.cloud.sonic.simple.cv.*;
import org.cloud.sonic.simple.models.http.RespEnum;
import org.cloud.sonic.simple.models.http.RespModel;
import org.cloud.sonic.simple.tools.FileTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

@Api(tags = "文件上传")
@RestController
@RequestMapping("/api/folder/upload")
public class UploadController {
    @Autowired
    private FileTool fileTool;
    @Autowired
    private RecordHandler recordHandler;
    @Autowired
    private AKAZEFinder akazeFinder;
    @Autowired
    private SIFTFinder siftFinder;
    @Autowired
    private SimilarityChecker similarityChecker;
    @Autowired
    private TemMatcher temMatcher;
    @Autowired
    private TextReader textReader;

    @WebAspect
    @ApiOperation(value = "上传文件", notes = "上传文件到服务器")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "file", value = "文件", dataTypeClass = MultipartFile.class),
            @ApiImplicitParam(name = "type", value = "文件类型(只能为keepFiles、imageFiles、recordFiles、logFiles、packageFiles)", dataTypeClass = String.class),
    })
    @PostMapping
    public RespModel<String> uploadFiles(@RequestParam(name = "file") MultipartFile file,
                                         @RequestParam(name = "type") String type) throws IOException {
        String url = fileTool.upload(type, file);
        if (url != null) {
            return new RespModel<>(RespEnum.UPLOAD_OK, url);
        } else {
            return new RespModel<>(RespEnum.UPLOAD_ERROR);
        }
    }

    @WebAspect
    @ApiOperation(value = "上传文件（录像分段上传）", notes = "上传文件到服务器")
    @ApiImplicitParams(value = {
//            @ApiImplicitParam(name = "file", value = "文件", dataTypeClass = MultipartFile.class),
            @ApiImplicitParam(name = "uuid", value = "文件uuid", dataTypeClass = String.class),
            @ApiImplicitParam(name = "index", value = "当前index", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "total", value = "index总数", dataTypeClass = Integer.class),
    })
    @PostMapping("/recordFiles")
    public RespModel<String> uploadRecord(@RequestBody List<byte[]> bytes,
                                          @RequestParam(name = "uuid") String uuid,
                                          @RequestParam(name = "width") int width,
                                          @RequestParam(name = "height") int height,
                                          @RequestParam(name = "index") int index,
                                          @RequestParam(name = "total") int total) {
        //先创建对应uuid的文件夹
        File uuidFolder = new File("recordFiles" + File.separator + uuid);
        if (!uuidFolder.exists()) {
            uuidFolder.mkdirs();
        }
        File local = new File(uuidFolder.getPath() + File.separator + index + ".mp4");
        RespModel<String> responseModel;
        try {
            recordHandler.record(local, bytes, width, height);
            responseModel = new RespModel<>(RespEnum.UPLOAD_OK);
        } catch (FrameRecorder.Exception e) {
            responseModel = new RespModel<>(RespEnum.UPLOAD_ERROR);
        }
        //如果当前是最后一个，就开始合并录像文件
        if (index == total - 1) {
            responseModel.setData(fileTool.merge(uuid, uuid, total));
        }
        return responseModel;
    }

//    @WebAspect
//    @ApiOperation(value = "上传文件", notes = "上传文件到服务器")
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(name = "file", value = "文件", dataTypeClass = MultipartFile.class),
//            @ApiImplicitParam(name = "type", value = "文件类型(只能为keepFiles、imageFiles、recordFiles、logFiles、packageFiles)", dataTypeClass = String.class),
//    })
//    @PostMapping
//    public RespModel<String> uploadFiles(@RequestParam(name = "file") MultipartFile file,
//                                         @RequestParam(name = "type") String type) throws IOException {
//        String url = fileTool.upload(type, file);
//        if (url != null) {
//            return new RespModel<>(RespEnum.UPLOAD_OK, url);
//        } else {
//            return new RespModel<>(RespEnum.UPLOAD_ERROR);
//        }
//    }
}
