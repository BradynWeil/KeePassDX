/*
 * Copyright 2019 Jeremy Jamet / Kunzisoft.
 *
 * This file is part of KeePass DX.
 *
 *  KeePass DX is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  KeePass DX is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with KeePass DX.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.kunzisoft.keepass.biometric

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import android.widget.ImageView
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat

import com.kunzisoft.keepass.R

@RequiresApi(api = Build.VERSION_CODES.M)
class FingerPrintAnimatedVector(context: Context, imageView: ImageView) {

    private val scanFingerprint: AnimatedVectorDrawableCompat? =
            AnimatedVectorDrawableCompat.create(context, R.drawable.scan_fingerprint)

    init {
        imageView.setImageDrawable(scanFingerprint)
    }

    private var animationCallback = object : Animatable2Compat.AnimationCallback() {
        override fun onAnimationEnd(drawable: Drawable) {
            imageView.post {
                scanFingerprint?.start()
            }
        }
    }

    fun startScan() {
        scanFingerprint?.registerAnimationCallback(animationCallback)

        if (scanFingerprint?.isRunning != true)
            scanFingerprint?.start()
    }

    fun stopScan() {
        scanFingerprint?.unregisterAnimationCallback(animationCallback)

        if (scanFingerprint?.isRunning == true)
            scanFingerprint.stop()
    }
}
