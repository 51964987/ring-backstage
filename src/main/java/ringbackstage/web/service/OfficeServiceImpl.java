package ringbackstage.web.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ringbackstage.common.enums.ResultCode;
import ringbackstage.common.exception.ResultException;
import ringbackstage.web.dao.office.OfficeMapper;
import ringbackstage.web.model.office.AdminOffice;
import ringbackstage.web.model.user.AdminUser;

@Service
public class OfficeServiceImpl implements OfficeService{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private OfficeMapper officeMapper;

	@Override
	public int add(AdminOffice office,AdminUser loginUser) throws ResultException {
		try {
			office.setDelFlag("0");
			office.setCreateBy(loginUser.getLoginName());
			office.setCreateDate(new Date());
			office.setId(UUID.randomUUID().toString());
			return officeMapper.add(office);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}

	@Override
	public int delete(AdminOffice office,AdminUser loginUser) throws ResultException {
		try {
			office.setUpdateBy(loginUser.getLoginName());
			office.setUpdateDate(new Date());
			return officeMapper.delete(office);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}

	@Override
	public int update(AdminOffice office,AdminUser loginUser) throws ResultException {
		try {
			office.setUpdateBy(loginUser.getLoginName());
			office.setUpdateDate(new Date());
			return officeMapper.update(office);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ResultException(ResultCode.SERVER_ERROR);
		}
	}

	@Override
	public AdminOffice findById(String id)throws ResultException {
		return officeMapper.findById(id);
	}
	
	@Override
	public List<AdminOffice> findList(AdminOffice office)  throws ResultException {
		return officeMapper.findList(office);
	}
	
	@Override
	public List<AdminOffice> trees(AdminOffice office)  throws ResultException {
		return officeMapper.trees(office);
	}	

}
