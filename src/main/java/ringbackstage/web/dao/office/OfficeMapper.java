package ringbackstage.web.dao.office;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import ringbackstage.web.model.office.AdminOffice;

@Mapper
public interface OfficeMapper {
	int add(AdminOffice office)throws DataAccessException;
	int delete(AdminOffice office)throws DataAccessException;
	int update(AdminOffice office)throws DataAccessException;
	AdminOffice findById(String id)throws DataAccessException;
	List<AdminOffice> findList(AdminOffice office)throws DataAccessException;
	List<AdminOffice> trees(AdminOffice office)throws DataAccessException;
}
