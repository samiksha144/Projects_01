using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;



namespace EmailTrigger
{

    public static class Common
    {
        public static void GetExpiryDates()
        {
            string con = "Data Source= yourServerName;Initial Catalog= yourDatabaseName;Integrated Security=True;";

            string storedProcedure = "get_pwd_expiry_date";

            using (SqlConnection connection = new SqlConnection(con))
            {
                connection.Open();
                using (SqlCommand cmd = new SqlCommand(storedProcedure, connection))
                {
                    cmd.CommandType = CommandType.StoredProcedure;
                    DataTable dt = new DataTable();
                    dt.Load(cmd.ExecuteReader());
                    Program.SendMail(dt);

                }
            }
        }
    }
}