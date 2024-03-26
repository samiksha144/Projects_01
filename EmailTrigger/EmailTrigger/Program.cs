using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.Common;
using System.Data.SqlClient;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Net.Mail;
using System.Net;

namespace EmailTrigger
{

    public class Program
    {


        public static void Main(string[] args)
        {

            Common.GetExpiryDates();

        }

        public static void SendMail(DataTable dt)
        {
            int portNumber = int.Parse(ConfigurationManager.AppSettings["PortNumber"]);
            string smtpServer = ConfigurationManager.AppSettings["SMTPServer"];
            string emailId = ConfigurationManager.AppSettings["MailId"];
            string ccMail1 = ConfigurationManager.AppSettings["CCMail1"];
            string ccMail2 = ConfigurationManager.AppSettings["CCMail2"];
            string redDays = ConfigurationManager.AppSettings["RedDays"];
            string amberDays = ConfigurationManager.AppSettings["AmberDays"];
            string yellowDays = ConfigurationManager.AppSettings["YellowDays"];
            string red = ConfigurationManager.AppSettings["LessThan7Days"];
            string amber = ConfigurationManager.AppSettings["8-15Days"];
            string yellow = ConfigurationManager.AppSettings["16-30Days"];
            DateTime expiryDate;


            var groups = dt.AsEnumerable()
               .Where(row => !string.IsNullOrEmpty(row.Field<string>("product_owner_email")) || !string.IsNullOrEmpty(row.Field<string>("Distribution_list_email")))
               .GroupBy(a => new
               {
                   ProductOwnerEmail = a.Field<string>("product_owner_email"),
                   DistributionEmail = a.Field<string>("Distribution_list_email")
               })
                .ToDictionary(g => g.Key, g => g.ToList());

            for (int i = 0; i < groups.Count; i++)
            {
                string environment = ConfigurationManager.AppSettings["Environment"];

                var email = groups.ElementAt(i);
                var productOwnerEmail = email.Key.ProductOwnerEmail;
                var distributionEmail = email.Key.DistributionEmail;

                var applications = String.Join(", ", email.Value.Select(a => a.Field<string>("system_name").Trim()).Distinct());
                //var dtfilter = "logins" + String.Join(",", email.Value.Select(a => a.Field<string>("login_name")));


                var rows = from row in dt.AsEnumerable()
                           group row by new
                           {
                               ProductOwnerEmail = row.Field<string>("product_owner_email"),
                               DistributionEmail = row.Field<string>("Distribution_list_email")
                           }
                                   into grp
                           where grp.Key.ProductOwnerEmail == productOwnerEmail && grp.Key.DistributionEmail == distributionEmail
                           select grp;


                DataTable dt1 = rows.SelectMany(g => g).CopyToDataTable();
                DataTable dtLessThan7Days = new DataTable();
                DataTable dt8to15Days = new DataTable();
                DataTable dt16to30Days = new DataTable();

                dt1.Columns.Add("Environment", typeof(string));

                foreach (DataRow row in dt1.Rows)
                {
                    row["Environment"] = environment;
                }

                dtLessThan7Days.Columns.AddRange(new DataColumn[]
                {
                        new DataColumn("ENVIRONMENT"),
                        new DataColumn("LOGIN NAME"),
                        new DataColumn("SERVER NAME"),
                        new DataColumn("DATABASE NAME"),
                        new DataColumn("EXPIRY DATE (PST)")
                });

                dt8to15Days.Columns.AddRange(new DataColumn[]
                {
                        new DataColumn("ENVIRONMENT"),
                        new DataColumn("LOGIN NAME"),
                        new DataColumn("SERVER NAME"),
                        new DataColumn("DATABASE NAME"),
                        new DataColumn("EXPIRY DATE (PST)")
                });

                dt16to30Days.Columns.AddRange(new DataColumn[]
                {
                        new DataColumn("ENVIRONMENT"),
                        new DataColumn("LOGIN NAME"),
                        new DataColumn("SERVER NAME"),
                        new DataColumn("DATABASE NAME"),
                        new DataColumn("EXPIRY DATE (PST)")
                });


                foreach (DataRow row in dt1.Rows)
                {
                    expiryDate = DateTime.ParseExact(row["password_expiry_date"].ToString(), "dd-MM-yyyy HH:mm:ss", CultureInfo.InvariantCulture);
                    int daysLeft = (expiryDate - DateTime.Now).Days;

                    if (daysLeft <= Convert.ToInt32(redDays))
                    {
                        dtLessThan7Days.Rows.Add(row["Environment"].ToString(),
                                     row["login_name"].ToString(),
                                     row["server_name"].ToString(),
                                     row["database_name"].ToString(),
                                     expiryDate.ToString("dd/MM/yyyy"));
                    }

                    else if (daysLeft <= Convert.ToInt32(amberDays.Split('-')[1]) && daysLeft >= Convert.ToInt32(amberDays.Split('-')[0]))
                    {
                        dt8to15Days.Rows.Add(row["Environment"].ToString(),
                                     row["login_name"].ToString(),
                                     row["server_name"].ToString(),
                                     row["database_name"].ToString(),
                                     expiryDate.ToString("dd/MM/yyyy"));
                    }

                    else if (daysLeft >= Convert.ToInt32(yellowDays))
                    {
                        dt16to30Days.Rows.Add(row["Environment"].ToString(),
                                     row["login_name"].ToString(),
                                     row["server_name"].ToString(),
                                     row["database_name"].ToString(),
                                     expiryDate.ToString("dd/MM/yyyy"));
                    }

                }

                MailMessage emailMessage = new MailMessage();

                StringBuilder sb = new StringBuilder();

                sb.Append("<p>Hello,</p>");
                sb.Append("<p>Please find below your respective applications for which account passwords needs to be rotated and updated to meet security standards.</p>");
                sb.Append("<br>");

                //First Table

                if (dtLessThan7Days.Rows.Count > 0)
                {
                    sb.Append("<table style='border-collapse:collapse; border:1px; table-layout:fixed; width:700px;'>");
                    sb.Append("<th colspan='5'; style='background-color:#4C72AA; padding:8px; border:1px solid #000000; text-align:center; color:White;font-family:Verdana;font-size:13px;word-break:break-all'>");
                    sb.Append("Password has expired or It will expire within 7 Days");
                    sb.Append("</th>");

                    sb.Append("<tr>");
                    foreach (DataColumn dc in dtLessThan7Days.Columns)
                    {
                        sb.Append("<th style='background-color:#4C72AA;padding:3px;border:1px solid #000000;text-align:left;color:White;font-family:Verdana;font-size:11px;word-break:break-all;'>");
                        sb.Append(dc.ColumnName);
                        sb.Append("</th>");
                    }
                    sb.Append("</tr>");

                    HashSet<string> uniqueRows = new HashSet<string>();

                    foreach (DataRow dr in dtLessThan7Days.Rows)
                    {
                        string rowString = String.Join(",", dr.ItemArray);

                        if (!uniqueRows.Contains(rowString))
                        {

                            uniqueRows.Add(rowString);

                            sb.Append("<tr>");

                            foreach (DataColumn dc in dtLessThan7Days.Columns)
                            {
                                string rowColor = "";

                                if (dc.ColumnName == "EXPIRY DATE (PST)")
                                {
                                    expiryDate = DateTime.Parse(dr[dc.ColumnName].ToString());

                                    int daysLeft = (expiryDate - DateTime.Now).Days;

                                    if (daysLeft <= Convert.ToInt32(redDays))
                                    {
                                        rowColor = red;
                                    }

                                }

                                sb.Append("<td style='background-color:" + rowColor + "; padding:3px;border:1px solid #ccc;word-break:break-all;width:700px;'>");
                                sb.Append(dr[dc.ColumnName].ToString());
                                sb.Append("</td>");
                            }
                            sb.Append("</tr>");
                        }
                    }

                    sb.Append("</table>");
                }

                sb.Append("<br>");
                sb.Append("<br>");


                //Second Table

                if (dt8to15Days.Rows.Count > 0)
                {
                    sb.Append("<table style='border-collapse:collapse; border:1px; table-layout:fixed; width:700px;'>");
                    sb.Append("<th colspan='5'; style='background-color:#4C72AA; padding:8px; border:1px solid #000000; text-align:center; color:White;font-family:Verdana;font-size:13px;word-break:break-all'>");
                    sb.Append("Password will expire in 8-15 Days");
                    sb.Append("</th>");

                    sb.Append("<tr>");
                    foreach (DataColumn dc in dt8to15Days.Columns)
                    {
                        sb.Append("<th style='background-color:#4C72AA;padding:3px;border:1px solid #000000;text-align:left;color:White;font-family:Verdana;font-size:11px;word-break:break-all;'>");
                        sb.Append(dc.ColumnName);
                        sb.Append("</th>");
                    }
                    sb.Append("</tr>");

                    HashSet<string> uniqueRows = new HashSet<string>();

                    foreach (DataRow dr in dt8to15Days.Rows)
                    {
                        string rowString = String.Join(",", dr.ItemArray);

                        if (!uniqueRows.Contains(rowString))
                        {

                            uniqueRows.Add(rowString);

                            sb.Append("<tr>");

                            foreach (DataColumn dc in dt8to15Days.Columns)
                            {

                                string rowColor = "";

                                if (dc.ColumnName == "EXPIRY DATE (PST)")
                                {
                                    expiryDate = DateTime.Parse(dr[dc.ColumnName].ToString());

                                    int daysLeft = (expiryDate - DateTime.Now).Days;


                                    if (daysLeft <= Convert.ToInt32(amberDays.Split('-')[1]) && daysLeft >= Convert.ToInt32(amberDays.Split('-')[0]))
                                    {
                                        rowColor = amber;
                                    }

                                }

                                sb.Append("<td style='background-color:" + rowColor + "; padding:3px;border:1px solid #ccc;word-break:break-all;width:700px;'>");
                                sb.Append(dr[dc.ColumnName].ToString());
                                sb.Append("</td>");
                            }
                            sb.Append("</tr>");
                        }
                    }

                    sb.Append("</table>");
                }


                sb.Append("<br>");
                sb.Append("<br>");

                //Third table

                if (dt16to30Days.Rows.Count > 0)
                {
                    sb.Append("<table style='border-collapse:collapse; border:1px; table-layout:fixed; width:700px;'>");
                    sb.Append("<th colspan='5'; style='background-color:#4C72AA; padding:8px; border:1px solid #000000; text-align:center; color:White;font-family:Verdana;font-size:13px;word-break:break-all'>");
                    sb.Append("Password will expire in 16-30 Days");
                    sb.Append("</th>");

                    sb.Append("<tr>");
                    foreach (DataColumn dc in dt16to30Days.Columns)
                    {
                        sb.Append("<th style='background-color:#4C72AA;padding:3px;border:1px solid #000000;text-align:left;color:White;font-family:Verdana;font-size:11px;word-break:break-all;'>");
                        sb.Append(dc.ColumnName);
                        sb.Append("</th>");
                    }
                    sb.Append("</tr>");

                    HashSet<string> uniqueRows = new HashSet<string>();

                    foreach (DataRow dr in dt16to30Days.Rows)
                    {
                        string rowString = String.Join(",", dr.ItemArray);

                        if (!uniqueRows.Contains(rowString))
                        {

                            uniqueRows.Add(rowString);

                            sb.Append("<tr>");

                            foreach (DataColumn dc in dt16to30Days.Columns)
                            {

                                string rowColor = "";

                                if (dc.ColumnName == "EXPIRY DATE (PST)")
                                {
                                    expiryDate = DateTime.Parse(dr[dc.ColumnName].ToString());

                                    int daysLeft = (expiryDate - DateTime.Now).Days;


                                    if (daysLeft >= Convert.ToInt32(yellowDays))
                                    {
                                        rowColor = yellow;
                                    }
                                }

                                sb.Append("<td style='background-color:" + rowColor + "; padding:3px;border:1px solid #ccc;word-break:break-all;width:700px;'>");
                                sb.Append(dr[dc.ColumnName].ToString());
                                sb.Append("</td>");
                            }
                            sb.Append("</tr>");
                        }

                    }
                    sb.Append("</table>");
                }

                sb.Append("<p>Thanks,</p>");
                sb.Append("<p>Team</p>");


                SmtpClient SMTPServer = new SmtpClient(smtpServer, portNumber);
                SMTPServer.EnableSsl = true;
                SMTPServer.UseDefaultCredentials = false;
                SMTPServer.Credentials = new NetworkCredential(emailId, "yourPassword");

                emailMessage = new MailMessage();

                if (!string.IsNullOrEmpty(emailId))
                {
                    emailMessage.From = new MailAddress(emailId);
                }

                if (!string.IsNullOrEmpty(productOwnerEmail))
                {
                    emailMessage.To.Add(productOwnerEmail);
                }

                if (!string.IsNullOrEmpty(distributionEmail))
                {
                    emailMessage.To.Add(distributionEmail);
                }

                if (!string.IsNullOrEmpty(ccMail1))
                {
                    emailMessage.CC.Add(ccMail1);
                }

                if (!string.IsNullOrEmpty(ccMail2))
                {
                    emailMessage.CC.Add(ccMail2);
                }

                emailMessage.Body = sb.ToString();
                emailMessage.Subject = "Password rotation is required for Applications : " + applications;
                emailMessage.Priority = MailPriority.High;
                emailMessage.IsBodyHtml = true;

                SMTPServer.Send(emailMessage);


                dtLessThan7Days.Clear();
                dt8to15Days.Clear();
                dt16to30Days.Clear();

            }

        }
    }
}