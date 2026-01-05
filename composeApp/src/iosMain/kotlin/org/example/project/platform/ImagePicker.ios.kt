@file:OptIn(ExperimentalForeignApi::class)

package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import platform.Foundation.NSData
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.NSURL
import platform.Foundation.NSUUID
import platform.Foundation.create
import platform.Foundation.dataWithContentsOfURL
import platform.Foundation.writeToFile
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerEditedImage
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
import platform.UIKit.UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.UIKit.UIViewController
import platform.darwin.NSObject
import platform.posix.memcpy

@Composable
actual fun rememberImagePicker(onImagePicked: (String?) -> Unit): ImagePickerLauncher {
    return remember {
        object : ImagePickerLauncher {
            override fun pickFromGallery() {
                presentPicker(source = UIImagePickerControllerSourceTypePhotoLibrary, onImagePicked = onImagePicked)
            }

            override fun capturePhoto() {
                val source: UIImagePickerControllerSourceType =
                    if (UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceTypeCamera)) {
                        UIImagePickerControllerSourceTypeCamera
                    } else {
                        UIImagePickerControllerSourceTypePhotoLibrary
                    }
                presentPicker(source = source, onImagePicked = onImagePicked)
            }
        }
    }
}

@Composable
actual fun rememberUriPainter(uri: String): Painter? {
    val bitmap: ImageBitmap = runCatching {
        val cleanUri = uri.removePrefix("file://")
        val url = NSURL.fileURLWithPath(cleanUri)
        val data: NSData? = NSData.dataWithContentsOfURL(url)
        val bytes = data?.toByteArray() ?: return null
        Image.makeFromEncoded(bytes).toComposeImageBitmap()
    }.getOrNull() ?: return null
    return BitmapPainter(bitmap)
}

private fun presentPicker(source: UIImagePickerControllerSourceType, onImagePicked: (String?) -> Unit) {
    val controller = topViewController() ?: return
    val picker = UIImagePickerController().apply {
        allowsEditing = false
        sourceType = source
        delegate = ComposeImagePickerDelegate(controller, onImagePicked)
    }
    controller.presentViewController(viewControllerToPresent = picker, animated = true, completion = null)
}

private class ComposeImagePickerDelegate(
    private val presenter: UIViewController,
    private val onImagePicked: (String?) -> Unit
) : NSObject(), UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {

    override fun imagePickerController(picker: UIImagePickerController, didFinishPickingMediaWithInfo: Map<Any?, *>) {
        val image = (didFinishPickingMediaWithInfo[UIImagePickerControllerEditedImage] ?: didFinishPickingMediaWithInfo[UIImagePickerControllerOriginalImage]) as? UIImage
        val path = image?.let { persistTempImage(it) }
        presenter.dismissViewControllerAnimated(flag = true, completion = null)
        onImagePicked(path?.let { "file://$it" })
    }

    override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
        presenter.dismissViewControllerAnimated(true, completion = null)
        onImagePicked(null)
    }
}

private fun persistTempImage(image: UIImage): String? {
    val data = UIImageJPEGRepresentation(image, 0.92) ?: return null
    val uuid = NSUUID().UUIDString
    val tempPath = NSTemporaryDirectory() + "/goticky_$uuid.jpg"
    return if (data.writeToFile(tempPath, true)) tempPath else null
}

@OptIn(ExperimentalForeignApi::class)
private fun NSData.toByteArray(): ByteArray {
    val length = this.length.toInt()
    val byteArray = ByteArray(length)
    memcpy(byteArray.refTo(0), this.bytes, this.length)
    return byteArray
}

private fun topViewController(controller: UIViewController? = UIApplication.sharedApplication.keyWindow?.rootViewController): UIViewController? {
    var current = controller
    while (current?.presentedViewController != null) {
        current = current.presentedViewController
    }
    return current
}
