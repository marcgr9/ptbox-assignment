package ro.marc.ptbox.ktor.rest

import io.ktor.http.*
import io.ktor.server.plugins.cors.*

class CorsConfig {

    fun CORSConfig.configureCors() {
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Head)

        anyHeader()
        anyHost()
    }

    fun CORSConfig.anyHeader() {
        HttpHeaders.safeHeader.forEach { allowHeader(it); exposeHeader(it) }
    }

    val HttpHeaders.safeHeader
        get() = allHeaders.filter { !isUnsafe(it) }

    val HttpHeaders.unsafeHeader
        get() = allHeaders.filter { isUnsafe(it) }

    val HttpHeaders.allHeaders
        get() = listOf(
            Accept, AcceptCharset, AcceptEncoding, AcceptLanguage, AcceptRanges,
            Age, Allow, ALPN, AuthenticationInfo, Authorization, CacheControl, Connection,
            ContentDisposition, ContentEncoding, ContentLanguage, ContentLength, ContentLocation,
            ContentRange, ContentType, Cookie, DASL, Date, DAV, Depth, Destination, ETag, Expect,
            Expires, From, Forwarded, Host, HTTP2Settings, If, IfMatch, IfModifiedSince,
            IfNoneMatch, IfRange, IfScheduleTagMatch, IfUnmodifiedSince, LastModified,
            Location, LockToken, Link, MaxForwards, MIMEVersion, OrderingType, Origin,
            Overwrite, Position, Pragma, Prefer, PreferenceApplied, ProxyAuthenticate,
            ProxyAuthenticationInfo, ProxyAuthorization, PublicKeyPins, PublicKeyPinsReportOnly,
            Range, Referrer, RetryAfter, ScheduleReply, ScheduleTag, SecWebSocketAccept,
            SecWebSocketExtensions, SecWebSocketKey, SecWebSocketProtocol, SecWebSocketVersion,
            Server, SetCookie, SLUG, StrictTransportSecurity, TE, Timeout, Trailer,
            TransferEncoding, Upgrade, UserAgent, Vary, Via, Warning, WWWAuthenticate,
            AccessControlAllowOrigin, AccessControlAllowMethods, AccessControlAllowCredentials,
            AccessControlAllowHeaders, AccessControlRequestMethod, AccessControlRequestHeaders,
            AccessControlExposeHeaders, AccessControlMaxAge, XHttpMethodOverride, XForwardedHost,
            XForwardedServer, XForwardedProto, XForwardedFor, XRequestId, XCorrelationId
        )

}
