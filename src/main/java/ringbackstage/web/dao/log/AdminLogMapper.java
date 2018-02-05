package ringbackstage.web.dao.log;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ringbackstage.web.model.log.AdminLog;

@Mapper
public interface AdminLogMapper {
	int add(AdminLog adminLog)throws Exception;
	int delete(AdminLog adminLog)throws Exception;
	int update(AdminLog adminLog)throws Exception;
	AdminLog findById(String id)throws Exception;
	List<AdminLog> findList(AdminLog adminLog)throws Exception;
	
}
