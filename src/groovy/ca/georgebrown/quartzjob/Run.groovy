package ca.georgebrown.quartzjob

class Run {
    Date start
    Date end
    String msg

    Run() {
        start = new Date()
    }

    def endRun() {
        end = new Date()
    }
}
