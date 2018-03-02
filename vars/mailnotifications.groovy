ef notifyStatusChangeViaEmail(buildStatus) {
    def status

    switch (buildStatus) {
        case 'SUCCESS':
            status = 'is now green again!'
            break

        case 'UNSTABLE':
            status = 'has become unstable..'
            break

        case 'FAILURE':
            status = 'has turned RED 
:(
'
            break
    }

    emailext (
        subject: "Job '${env.JOB_NAME}' ${status}",
        body: "See ${env.BUILD_URL} for more details",
        recipientProviders: [
            [$class: 'DevelopersRecipientProvider'], 
            [$class: 'RequesterRecipientProvider']
        ]
    )
}

pipeline {
    ...

    post {
        changed {
            // Will trigger only when job status changes: GREEN -> RED, RED -> GREEN, etc
            notifyStatusChangeViaEmail(currentBuild.currentResult)
        }
    }


}
