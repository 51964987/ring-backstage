package ringbackstage.web.service;

import java.util.List;

import ringbackstage.common.exception.ResultException;
import ringbackstage.web.model.office.AdminOffice;
import ringbackstage.web.model.user.AdminUser;

public interface OfficeService {
	int add(AdminOffice office,AdminUser loginUser)throws ResultException;
	int delete(AdminOffice office,AdminUser loginUser)throws ResultException;
	int update(AdminOffice office,AdminUser loginUser)throws ResultException;
	AdminOffice findById(String id)throws ResultException;
	List<AdminOffice> findList(AdminOffice office)throws ResultException;
	List<AdminOffice> trees(AdminOffice office)throws ResultException;
}
