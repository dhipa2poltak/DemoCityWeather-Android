package com.dpfht.android.democityweather.framework.commons.test

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.schedulers.ExecutorScheduler.ExecutorWorker
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.TimeUnit

class RxImmediateSchedulerRule : TestRule {

  private val immediate: Scheduler = object : Scheduler() {
    override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
      // this prevents StackOverflowErrors when scheduling with a delay
      return super.scheduleDirect(run, 0, unit)
    }

    override fun createWorker(): Worker {
      return ExecutorWorker({ obj: Runnable -> obj.run() }, false, false)
    }
  }

  override fun apply(base: Statement, description: Description): Statement {
    return object : Statement() {

      @Throws(Throwable::class)
      override fun evaluate() {
        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
        try {
          base.evaluate()
        } finally {
          RxJavaPlugins.reset()
          RxAndroidPlugins.reset()
        }
      }
    }
  }
}
