CREATE PROCEDURE [dbo].[get_pwd_expiry_date]
AS
BEGIN
select * from gecs_job
where (password_expiry_date <= DATEADD(month,1,convert(date,getdate()))) 
or (password_expiry_date <= CONVERT(date,getdate()))
END