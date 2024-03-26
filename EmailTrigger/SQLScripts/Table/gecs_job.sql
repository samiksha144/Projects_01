CREATE TABLE [dbo].[gecs_job] (
    [login_id]                INT           IDENTITY (1, 1) NOT NULL,
    [system_name]             VARCHAR (MAX) NULL,
    [product_owner_email]     VARCHAR (MAX) NULL,
    [Distribution_list_email] VARCHAR (MAX) NULL,
    [login_name]              VARCHAR (MAX) NULL,
    [password]                VARCHAR (MAX) NULL,
    [database_name]           VARCHAR (MAX) NULL,
    [server_name]             VARCHAR (MAX) NULL,
    [password_expiry_date]    DATETIME      NULL,
    CONSTRAINT [PK_gecs_job] PRIMARY KEY CLUSTERED ([login_id] ASC)
);

