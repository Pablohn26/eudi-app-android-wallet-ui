/*
 * Copyright (c) 2023 European Commission
 *
 * Licensed under the EUPL, Version 1.2 or - as soon they will be approved by the European
 * Commission - subsequent versions of the EUPL (the "Licence"); You may not use this work
 * except in compliance with the Licence.
 *
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the Licence is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF
 * ANY KIND, either express or implied. See the Licence for the specific language
 * governing permissions and limitations under the Licence.
 */

package eu.europa.ec.businesslogic.config

import android.content.Context
import eu.europa.ec.businesslogic.BuildConfig
import eu.europa.ec.eudi.rqes.HashAlgorithmOID
import eu.europa.ec.eudi.rqes.core.RqesSigningLogger
import eu.europa.ec.eudi.rqesui.domain.entities.localization.LocalizableKey
import eu.europa.ec.eudi.rqesui.domain.extension.toUriOrEmpty
import eu.europa.ec.eudi.rqesui.infrastructure.config.DocumentRetrievalConfig
import eu.europa.ec.eudi.rqesui.infrastructure.config.EudiRQESUiConfig
import eu.europa.ec.eudi.rqesui.infrastructure.config.data.QtspData
import eu.europa.ec.resourceslogic.R
import java.net.URI

class RQESConfigImpl(
    val context: Context,
    override val signingLogger: RqesSigningLogger,
) : EudiRQESUiConfig {

    /**
     * The RQES UI SDK ships English only and falls back to [LocalizableKey.defaultTranslation]
     * for any language it does not find here, keyed by ISO language code.
     */
    override val translations: Map<String, Map<LocalizableKey, String>>
        get() = mapOf(
            "en" to LocalizableKey.entries.associateWith { it.defaultTranslation() },
            "es" to mapOf(
                LocalizableKey.SignDocument to "Firmar documento",
                LocalizableKey.CancelSignProcessTitle to "¿Cancelar el proceso de firma?",
                LocalizableKey.CancelSignProcessSubtitle to "Si cancelas, volverás a la lista de documentos sin firmar tu documento",
                LocalizableKey.CancelSignProcessSecondaryText to "Cancelar la firma",
                LocalizableKey.CancelSignProcessPrimaryText to "Continuar con la firma",
                LocalizableKey.SelectDocumentTitle to "Documento",
                LocalizableKey.SelectDocumentSubtitle to "Elige un documento de tu dispositivo para firmarlo electrónicamente.",
                LocalizableKey.SigningService to "Servicio de firma",
                LocalizableKey.SelectSigningService to "Seleccionar el servicio de firma",
                LocalizableKey.SelectSigningServiceSubtitle to "El Servicio de Firma Remota permite firmar documentos en línea de forma segura.",
                LocalizableKey.SelectServiceTitle to "Servicios de firma",
                LocalizableKey.SelectServiceSubtitle to "Selecciona el Servicio de Firma que se usará para emitir un certificado digital",
                LocalizableKey.SelectSigningCertificateTitle to "Seleccionar el certificado de firma",
                LocalizableKey.SelectCertificateSubtitle to "El certificado de firma se usa para verificar tu identidad y está vinculado a tu firma electrónica.",
                LocalizableKey.SelectSigningCertificateSubtitle to "Selecciona un certificado digital para firmar tu documento",
                LocalizableKey.SuccessDescription to "Has firmado tu documento correctamente.",
                LocalizableKey.View to "VER",
                LocalizableKey.Close to "Cerrar",
                LocalizableKey.Cancel to "Cancelar",
                LocalizableKey.Continue to "Continuar",
                LocalizableKey.Done to "Hecho",
                LocalizableKey.Share to "Compartir",
                LocalizableKey.SharingDocument to "¿Compartir el documento?",
                LocalizableKey.CloseSharingMessage to "Si cierras, volverás al panel sin guardar ni compartir el documento.",
                LocalizableKey.GenericErrorButtonRetry to "REINTENTAR",
                LocalizableKey.GenericErrorMessage to "¡Vaya! Algo ha salido mal",
                LocalizableKey.GenericServiceErrorMessage to "Parece que el Servicio de Firma RQES no está disponible. Inténtalo de nuevo más tarde.",
                LocalizableKey.GenericErrorDescription to "Si el problema persiste, ponte en contacto con el servicio de asistencia",
                LocalizableKey.GenericErrorDocumentNotFound to "No se han encontrado datos del documento",
                LocalizableKey.GenericErrorDocumentMultipleNotSupported to "Actualmente no se admiten varios documentos",
                LocalizableKey.GenericErrorQtspNotFound to "No se ha encontrado el QTSP seleccionado",
                LocalizableKey.GenericErrorCertificatesNotFound to "No se han encontrado certificados",
                LocalizableKey.SigningCertificate to "Certificado de firma",
                LocalizableKey.SigningCertificates to "Certificados de firma",
                LocalizableKey.Certificate to "Certificado ${LocalizableKey.ARGUMENTS_SEPARATOR}",
            ),
        )

    override val qtsps: List<QtspData>
        get() = listOf(
            QtspData(
                name = "Wallet-Centric",
                endpoint = "https://walletcentric.signer.eudiw.dev/csc/v2".toUriOrEmpty(),
                tsaUrl = "https://timestamp.sectigo.com/qualified",
                clientId = "wallet-client",
                clientSecret = "somesecret2",
                authFlowRedirectionURI = URI.create(BuildConfig.RQES_DEEPLINK),
                hashAlgorithm = HashAlgorithmOID.SHA_256,
            )
        )

    override val printLogs: Boolean get() = BuildConfig.DEBUG

    override val documentRetrievalConfig: DocumentRetrievalConfig
        get() = DocumentRetrievalConfig.X509Certificates(
            context,
            listOf(
                R.raw.pidissuerca02_cz,
                R.raw.pidissuerca02_ee,
                R.raw.pidissuerca02_eu,
                R.raw.pidissuerca02_lu,
                R.raw.pidissuerca02_nl,
                R.raw.pidissuerca02_pt,
                R.raw.pidissuerca02_ut
            )
        )
}