import * as React from "react";
import customAxios from "../utils/MyAxios";
import Constants from "../Constants";
import CommonFunctions from "../services/CommonFunction";
export default function IssueLisitng() {
  const commFunc = new CommonFunctions();
  const [issues, setIssue] = React.useState([]);
  const renderIssues = (issues) => {};
  React.useEffect(() => {
    customAxios.get(Constants.GETISSUES).then(
      (res) => {
        renderIssues(res.data);
      },
      (error) => {
        console.error(error);
        commFunc.showAlertMessage(
          "Error while fetching issues",
          "error",
          3000,
          "bottom"
        );
      }
    );
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return <>test</>;
}
