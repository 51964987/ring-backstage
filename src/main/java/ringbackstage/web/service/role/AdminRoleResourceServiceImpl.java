package ringbackstage.web.service.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import ringbackstage.common.enums.ResultCode;
import ringbackstage.common.exception.ResultException;
import ringbackstage.web.dao.resource.AdminResourceMapper;
import ringbackstage.web.dao.role.AdminRoleResourceMapper;
import ringbackstage.web.model.resource.AdminResource;
import ringbackstage.web.model.role.AdminRoleResource;

@Service
public class AdminRoleResourceServiceImpl implements AdminRoleResourceService{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AdminRoleResourceMapper adminRoleResourceMapper;
	@Autowired
	private AdminResourceMapper adminResourceMapper;
	
	@Transactional
	@Override
	public int accredit(String roleId, List<AdminRoleResource> roleResources) throws ResultException {
		try {
			if(StringUtils.isNotEmpty(roleId)){
				adminRoleResourceMapper.deleteByRoleId(roleId);
			}
			if(roleResources != null && roleResources.size() > 0){
				return adminRoleResourceMapper.add(roleResources);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
		return 0;
	}
	@Override
	public List<AdminRoleResource> findList(AdminRoleResource adminRoleResource) throws ResultException {
		try {
			return adminRoleResourceMapper.findList(adminRoleResource);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}
	
	@Override
	public JSONArray findBusResource(Set<String> roleIds) throws ResultException {
		try {
			List<AdminResource> resources = adminResourceMapper.findBusResourceByRoleIds(roleIds);
			if(resources != null && resources.size() > 0){
				return JSONArray.parseArray(JSON.toJSONString(resources));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
		return null;
	}
	
	private void setChild(List<AdminResource> resources, Map<String, List<AdminResource>> parentIdMaps) {
		for(AdminResource tmp : resources){
			List<AdminResource> parentChild = parentIdMaps.get(tmp.getId());
			if(parentChild != null){
				tmp.setChild(parentChild);
			}
			if(tmp.getChild() != null && tmp.getChild().size() > 0){
				setChild(tmp.getChild(), parentIdMaps);
			}
		}
	}
	@Override
	public JSONArray findBackResource(Set<String> roleIds) throws ResultException {
		try {
			List<AdminResource> resources = adminResourceMapper.findBackResourceByRoleIds(roleIds);
			if(resources != null && resources.size() > 0){
				Map<String, List<AdminResource>> parentIdMaps = new HashMap<>();//parentId:child
				List<AdminResource> result = new ArrayList<>();
				for(AdminResource tmp : resources){
					if(StringUtils.isNotEmpty(tmp.getParentId())){
						List<AdminResource> child = parentIdMaps.get(tmp.getParentId());
						if(child == null){
							child = new ArrayList<>();
						}
						child.add(tmp);
						parentIdMaps.put(tmp.getParentId(), child);
					}else{
						result.add(tmp);
					}
				}
				setChild(result,parentIdMaps);
				return JSONArray.parseArray(JSON.toJSONString(result));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
		return null;
	}	
}