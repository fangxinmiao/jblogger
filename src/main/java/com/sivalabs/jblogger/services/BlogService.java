/**
 * 
 */
package com.sivalabs.jblogger.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sivalabs.jblogger.domain.BlogOverview;
import com.sivalabs.jblogger.domain.TimePeriod;
import com.sivalabs.jblogger.entities.PageView;
import com.sivalabs.jblogger.repositories.CommentRepository;
import com.sivalabs.jblogger.repositories.PageViewRepository;
import com.sivalabs.jblogger.repositories.PostRepository;
import com.sivalabs.jblogger.utils.CommonUtils;

/**
 * @author Siva
 *
 */
@Service
@Transactional
public class BlogService 
{
	@Autowired private PostRepository postRepository;
	@Autowired private CommentRepository commentRepository;
	@Autowired private PageViewRepository pageViewRepository;

	public BlogOverview getBlogOverView(TimePeriod timePeriod)
	{
		BlogOverview overview = new BlogOverview();
		long postsCount = postRepository.count();
		overview.setPostsCount(postsCount);

		long commentsCount = commentRepository.count();
		overview.setCommentsCount(commentsCount);
		
		Date today = new Date();
		Date startDate = CommonUtils.getStartOfDay(today);
		Date endDate = CommonUtils.getEndOfDay(today);
		
		long todayViewCount = pageViewRepository.countByVisitTimeBetween(startDate, endDate);
		overview.setTodayViewCount(todayViewCount);
		
		Date yesterday = CommonUtils.getYesterDay(today);
		startDate = CommonUtils.getStartOfDay(yesterday);
		endDate = CommonUtils.getEndOfDay(yesterday);
		long yesterdayViewCount = pageViewRepository.countByVisitTimeBetween(startDate, endDate);
		overview.setYesterdayViewCount(yesterdayViewCount);
		
		startDate = CommonUtils.getWeekStartDay(today);
		endDate = CommonUtils.getWeekEndDay(today);
		
		long thisWeekViewCount = pageViewRepository.countByVisitTimeBetween(startDate, endDate);
		overview.setThisWeekViewCount(thisWeekViewCount);
		
		startDate = CommonUtils.getMonthStartDay(today);
		endDate = CommonUtils.getMonthEndDay(today);
		
		long thisMonthViewCount = pageViewRepository.countByVisitTimeBetween(startDate, endDate);
		overview.setThisMonthViewCount(thisMonthViewCount);
		
		long alltimeViewCount = postRepository.getTotalPostViewCount();
		overview.setAlltimeViewCount(alltimeViewCount);
		
		if(timePeriod == TimePeriod.ALL_TIME){
			startDate = CommonUtils.getDummyVeryOldDate();
			endDate = CommonUtils.getDummyVeryNewDate();
		} else if(timePeriod == TimePeriod.MONTH){
			startDate = CommonUtils.getMonthStartDay(today);
			endDate = CommonUtils.getMonthEndDay(today);
		} else if(timePeriod == TimePeriod.WEEK){
			startDate = CommonUtils.getWeekStartDay(today);
			endDate = CommonUtils.getWeekEndDay(today);
		} else if(timePeriod == TimePeriod.YESTERDAY){
			startDate = CommonUtils.getStartOfDay(yesterday);
			endDate = CommonUtils.getEndOfDay(yesterday);
		} else {
			startDate = CommonUtils.getStartOfDay(today);
			endDate = CommonUtils.getEndOfDay(today);
		}
		
		List<PageView> pageViews = pageViewRepository.findByVisitTimeBetween(startDate, endDate);
		overview.setPageViews(pageViews );
		return overview;
	}

}
