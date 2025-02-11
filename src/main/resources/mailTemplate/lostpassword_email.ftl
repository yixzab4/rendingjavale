<!DOCTYPE html>
<html>
  <head>
    <title><App Name> Application</title>
  </head>
  <body style="margin: 0px; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; color: #333333; background-color: #481267;">
    <center>
    <table style="width: 100%; height: 100%; background-color: #481267;">
      <tr><td style="width: 100%;">

        <table style="background-color: #ffffff; border-radius: 10px; width: 935px;">
          <tr>
            <td style="padding-bottom: 20px; padding-top: 10px; padding-left: 10px;"></td>

            <!-- HEADER MESSAGE -->
            <td style="padding-left: 30px; padding-bottom: 20px; padding-top: 10px;"><h2>Reset Password</h2></td>


            <td style="padding-bottom: 20px; padding-top: 10px; padding-left: 10px;">

            </td>
          </tr>

          <tr>
            <td style="text-align: center; padding-top: 20px;" valign="top">


            </td>
            <td colspan="2" style="padding-left: 30px; border-left: 2px solid #E7E7E8;">

              <!-- MESSAGE -->

              <p>A request was submitted to reset the password for your account. Click through on the link below to reset your password within the next 24 hours.</p>

              <p><a href="http://${hostUrl}/${pageName}?${encodedToken}">Password Reset</a></p>

              <!-- LEGALESE -->

              <div style="font-size: 12px;">
                <p>Please don't reply to this automatically generated email.</p>

                <p>What is this doing in my mailbox?</p>

                <p>If you did not intend to reset your password, you do not need to click through on the link in this email. If you did not request a link to reset your password, and are concerned that your account is at risk, then please contact us through customer support at http://XXXXXXXXXX.</p>

                <p>Thanks for using <App Name>!</p>
              </div>
            </td>
          </tr>
        </table>

      </td></tr>
    </table>
    </center>
  </body>
</html>