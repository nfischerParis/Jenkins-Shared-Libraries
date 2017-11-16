class Logger {
	String 	name				= ""
	File	file				= null
    def     traceFunc       	= null
	def 	traceLevels     	= [FATAL: 0, ERROR: 1, WARNING: 2, INFO: 3, VERBOSE: 4]
	String 	traceLevel      	= 'INFO'
	def 	traceHeaderEnabled	= true
	
	def Logger(String name) {
		this.name = name
        this.traceFunc = null
	}

	def Logger(String name, traceFunc) {
		this.name = name
        this.traceFunc = traceFunc
	}

	def Logger(String name, File file) {
		this.name = name
        this.file = new File(file.absolutePath)
		this.file.getParentFile().mkdirs()
		this.file.createNewFile()
		this.file.write("\n")
	}

	def setLevel(level) {
		this.traceLevel = level
	}
	
	def enableTraceHeader() {
		traceHeaderEnabled	= true
	}

	def disableTraceHeader() {
		traceHeaderEnabled	= false
	}

	def reportTrace(lvl, trace) {
		if (traceLevels[traceLevel] >= traceLevels[lvl]) {
			String finalTrace = (traceHeaderEnabled ? name + " [" + lvl + "]: " : "") + trace
			if (file != null)
				file << (finalTrace + "\n")
			else {
				if (traceFunc == null)
					println(finalTrace)
				else
					traceFunc(finalTrace)
			}
				
		}
		if (lvl == 'FATAL') {
			throw new RuntimeException('Fatal error: ' + trace)
		}
	}

	def reportVerboseTrace (trace) {
		this.reportTrace('VERBOSE', trace)
	}

	def reportInfoTrace (trace) {
		this.reportTrace('INFO', trace)
	}

	def reportWarningTrace (trace) {
		this.reportTrace('WARNING', trace)
	}

	def reportErrorTrace (trace) {
		this.reportTrace('ERROR', trace)
	}

}
