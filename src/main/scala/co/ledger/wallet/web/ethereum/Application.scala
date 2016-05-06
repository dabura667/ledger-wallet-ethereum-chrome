package co.ledger.wallet.web.ethereum

import biz.enef.angulate.Module.RichModule
import biz.enef.angulate._
import biz.enef.angulate.core.HttpService
import biz.enef.angulate.ext.RouteProvider
import co.ledger.wallet.core.utils.HexUtils
import co.ledger.wallet.web.ethereum.components.{LButton, NavigationBar, RefreshButton}
import co.ledger.wallet.web.ethereum.controllers.WindowController
import co.ledger.wallet.web.ethereum.controllers.wallet.{AccountController, ReceiveController, SendIndexController}
import co.ledger.wallet.web.ethereum.core.eth.Address
import co.ledger.wallet.web.ethereum.services.WindowService

import scala.scalajs.js
import scala.scalajs.js.JSApp

/**
  * Created by pollas_p on 28/04/2016.
  */

object Application extends JSApp{

  @scala.scalajs.js.annotation.JSExport
  override def main(): Unit = {
    implicit val module = angular.createModule("app", Seq("ngRoute"))
    _module = module

    // Components
    NavigationBar.init(module)
    RefreshButton.init(module)
    LButton.init(module)

    // Controllers
    WindowController.init(module)
    AccountController.init(module)
    SendIndexController.init(module)
    ReceiveController.init(module)

    // Services
    WindowService.init(module)

    module.config(initRoutes _)
    module.config(($compileProvider: js.Dynamic) => {
      $compileProvider.aHrefSanitizationWhitelist(js.RegExp("^\\s*(https?|ftp|mailto|file|chrome-extension):"))
      $compileProvider.imgSrcSanitizationWhitelist(js.RegExp("^\\s*(https?|ftp|mailto|file|chrome-extension):"))
    })
    module.run(initApp _)

    try {
      val address = "5884Fcfc9aa4d4A9F1B8580b9d375c9bBB74008A"
      println(s"$address => ${Address(address).toIBAN}")
    } catch {
      case er: Throwable => er.printStackTrace()
    }
  }

  def initApp($http: HttpService, $rootScope: js.Dynamic, $location: js.Dynamic) = {
    println("App initialized")
    $rootScope.location = $location
  }

  def initRoutes($routeProvider: RouteProvider) = {
    Routes.declare($routeProvider)
  }

  private var _module: RichModule = _
  def module = _module

}