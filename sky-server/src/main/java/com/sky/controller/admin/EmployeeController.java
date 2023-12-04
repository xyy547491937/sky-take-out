package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation("登录")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    @ApiOperation("新增员工")
    @PostMapping
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工{}",employeeDTO);

        // 调用service
        employeeService.save(employeeDTO);
        return Result.success("新增成功");

    }
    @GetMapping("/page")
    @ApiOperation("员工分页查询")
    public Result<PageResult>page (EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("员工分页插叙{}",employeePageQueryDTO);
       PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }
    /*
    * 禁用启用
    * */
    @ApiOperation("启动禁用员工账号")
    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable("status") Integer status,long id) {

        log.info("启用禁用员工账号{}{}",status,id);
        employeeService.startOrStop(status,id);
        return Result.success("修改成功");
    }

    /*
    * 查询当前员工信息
    * */

    @ApiOperation("根据用户id 查询")
    @GetMapping("/{id}")
    public Result<Employee> getUserById(@PathVariable Integer id) {

        log.info("根据id 查询员工信息:{}",id);

        Employee employee = employeeService.getUserById(id);
        return Result.success(employee);
    }

    /*
    * 修改员工
    * */

    @ApiOperation("修改员工")
    @PutMapping
    public Result update(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.update(employeeDTO);
        return Result.success();
    }


}
