import play.api.GlobalSettings
import play.api.mvc.RequestHeader
import info.schleichardt.play2.basicauth.CredentialsFromConfChecker
import info.schleichardt.play2.basicauth.Authenticator
import utils.Config

/**
 * Created by Engin Yoeyen on 11/10/14.
 */
object Global extends GlobalSettings {

  val requireBasicAuthentication = Authenticator(new CredentialsFromConfChecker)
  override def onRouteRequest(request: RequestHeader) = {
    if (Config.authenticationEnabled) {
      requireBasicAuthentication(request, () => super.onRouteRequest(request))
    }else{
      super.onRouteRequest(request)
    }
  }
}
