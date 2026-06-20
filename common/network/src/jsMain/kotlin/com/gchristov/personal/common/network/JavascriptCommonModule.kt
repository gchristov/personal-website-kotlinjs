package com.gchristov.personal.common.network

import co.touchlab.kermit.Logger
import com.gchristov.personal.common.network.http.ExpressHttpService
import com.gchristov.personal.common.network.http.HttpService

actual fun provideHttpService(log: Logger): HttpService = ExpressHttpService(log)
