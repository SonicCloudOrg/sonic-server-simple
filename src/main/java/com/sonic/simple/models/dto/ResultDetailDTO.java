package com.sonic.simple.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sonic.simple.models.base.TypeConverter;
import com.sonic.simple.models.domain.ResultDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@ApiModel("测试结果详情DTO 模型")
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultDetailDTO implements Serializable, TypeConverter<ResultDetailDTO, ResultDetail> {

    @ApiModelProperty(value = "id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "测试用例id", example = "1")
    private Integer caseId;

    @ApiModelProperty(value = "测试结果id", example = "1")
    private Integer resultId;

    @ApiModelProperty(value = "测试结果详情类型", example = "step")
    private String type;

    @ApiModelProperty(value = "测试结果详情描述", example = "点击xxx")
    private String des;

    @ApiModelProperty(value = "测试结果详情状态", example = "1")
    private Integer status;

    @ApiModelProperty(value = "设备id", example = "1")
    private Integer deviceId;

    @ApiModelProperty(value = "测试结果详情详细日志", example = "点击xpath://*[@text()='xxx']")
    private String log;

    @ApiModelProperty(value = "时间", example = "16:00:00")
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date time;
}
