#ifndef _OSMAND_LOGGING_H_
#define _OSMAND_LOGGING_H_

#include "Common.h"

namespace OsmAnd
{
    enum class LogSeverityLevel
    {
        Error,
        Warning,
        Debug,
        Info
    };

    void LogPrintf(LogSeverityLevel level, const char* format, ...);
}

#endif // _OSMAND_LOGGING_H_