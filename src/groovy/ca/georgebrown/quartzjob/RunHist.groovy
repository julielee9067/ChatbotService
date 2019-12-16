package ca.georgebrown.quartzjob

class RunHist {
    ArrayList<Run> runList = []

    int getCnt() {
        return runList.size()
    }
    Run getLast() {
        return runList.last()
    }

    def start(int limitHist = 0) {
        Run result = new Run()
        if(limitHist) {
            if(runList.size() >= limitHist ) {
                runList.remove(0)
            }
        }
        runList.add(result)
    }
    def end(String msg) {
        def lastRun = last
        lastRun.msg = msg
        lastRun.endRun()
    }
}
