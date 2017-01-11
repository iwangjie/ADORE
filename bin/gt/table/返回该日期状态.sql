-- 返回该日期状态
create function emp_what_holiday(@who_day varchar(20))
-- 1:公休日 2:工作日  3 休息日
	returns int
as
begin
	declare @holiday_res int,
			@week_res varchar(30),
			@changetype int
	
	select @week_res=datename(dw,@who_day)
	
	-- 1:公休日 2:工作日  3 休息日
	select @changetype=changetype from HrmPubHoliday where holidaydate=@who_day
	
	
	if @changetype=1 or @changetype=2 or @changetype=3
		 begin
		 	set @holiday_res=@changetype
		 end
	else
		begin
			if @week_res ='星期六' or @week_res ='星期日'
				begin
					set @holiday_res=3
				end
			else 
				begin
					set @holiday_res=2
				end
	 	end
	return @holiday_res
end