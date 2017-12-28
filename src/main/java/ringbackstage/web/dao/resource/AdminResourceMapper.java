package ringbackstage.web.dao.resource;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ringbackstage.web.model.resource.AdminResource;

@Mapper
public interface AdminResourceMapper {
	int add(AdminResource adminResource)throws Exception;
	int delete(AdminResource adminResource)throws Exception;
	int update(AdminResource adminResource)throws Exception;
	AdminResource findById(String id)throws Exception;
	List<AdminResource> findList(AdminResource adminResource)throws Exception;
	List<AdminResource> trees(AdminResource adminResource)throws Exception;
	List<AdminResource> findTree(AdminResource adminResource)throws Exception;
}
