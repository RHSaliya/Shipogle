import AlertMessage from "../components/AlertMessage";

class CommonFunctions {
  static backgroundUrl = "";
  static googleMapObjectStatus = false;
  constructor() {
    this.fetchUrl();
  }

  showAlertMessage(message, type, duration, position) {
    document
      .querySelector("body")
      .appendChild(
        <AlertMessage
          message={message}
          messageType={type}
          duration={duration}
          position={position}
        ></AlertMessage>
      );
  }

  fetchUrl() {
    const randomNumber = Math.floor(Math.random * 1300);
    fetch(
      `https://source.unsplash.com/collection/11649432/800x600/?sig=${randomNumber}`
    ).then(
      (response) => {
        CommonFunctions.backgroundUrl = response.url;
        window.localStorage.setItem(
          "backgroundUrlLogin",
          CommonFunctions.backgroundUrl
        );
      },
      (error) => {
        CommonFunctions.backgroundUrl = "";
        window.localStorage.setItem(
          "backgroundUrlLogin",
          CommonFunctions.backgroundUrl
        );
        console.error(error?.message);
      }
    );
  }

  getUrl() {
    return CommonFunctions.backgroundUrl;
  }

  googleObjectDefinedStatus(status) {
    console.log(status);
    CommonFunctions.googleMapObjectStatus = status;
    console.log(CommonFunctions.googleMapObjectStatus);
  }
}

export default CommonFunctions;
