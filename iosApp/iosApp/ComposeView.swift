import Foundation
import shared
import SwiftUI

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        return MainViewControllerKt.MainViewController(screenSizePx:  Int32(UIScreen.main.bounds.size.width))
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}
